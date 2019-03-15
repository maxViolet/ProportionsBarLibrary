package ru.startandroid.proportionsbarlibrary;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;

public class ProportionsBar extends View {
    //draw round edges
    private boolean showRoundEdges;
    //curveOfEdges of the round edges of the custom view
    private double curveOfEdges = 1.4;
    //show GAPS
    private boolean showGaps = true;
    //GAPs' size in % of the container view's width
    private double gapSize = 1.0;
    //GAPs' color
    private int gapColor = getResources().getColor(R.color.white);
    //minimal segment value to be shown in % of the bar width (meaning: values between >0% and <2% will be shown as 2% section)
    private int minimalSegmentValue = 2;
    //list of data values
    private ArrayList<Integer> valueList = new ArrayList<>();

    private boolean firstLaunch = true;
    private boolean animated = false;
    private int animationDuration = 1500;

    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    public List<Integer> percentValueList = new ArrayList<>();
    private List<Integer> colorsInt = new ArrayList<>();
    private Queue<Integer> colorQueueInt = new ArrayDeque<>();

    public ProportionsBar showRoundEdges(boolean show) {
        this.showRoundEdges = show;
        return this;
    }

    public ProportionsBar curveOfEdges(double curve) {
        this.curveOfEdges = curve;
        return this;
    }

    public ProportionsBar showGaps(boolean show) {
        this.showGaps = show;
        return this;
    }

    public ProportionsBar gapSize(double size) {
        this.gapSize = size;
        return this;
    }

    public ProportionsBar gapColor(int gapColor) {
        this.gapColor = gapColor;
        return this;
    }

    public ProportionsBar minimalSegmentValue(int minimalSegmentValue) {
        this.minimalSegmentValue = minimalSegmentValue;
        return this;
    }

    public ProportionsBar animated(boolean animated) {
        this.animated = animated;
        return this;
    }

    public ProportionsBar animationDuration(int duration) {
        this.animationDuration = duration;
        return this;
    }

//    public ProportionsBar addValue(int value) {
//        this.valueList.add(value);
//        return this;
//    }

    public ProportionsBar addValues(Integer... values) {
        this.valueList.addAll(Arrays.asList(values));
        ArrayList<Integer> k = getPercentValues(valueList);
        //fill percent list for segment drawing
        for (int i = 0; i < valueList.size(); i++) {
            percentValueList.add(k.get(i));
        }
        return this;
    }

    public ProportionsBar addIntColor(Integer color) {
        this.colorsInt.add(color);
        return this;
    }

    public ProportionsBar addIntColors(Integer... colors) {
        this.colorsInt.addAll(Arrays.asList(colors));
        return this;
    }

    public ProportionsBar(Context context) {
        super(context);
    }

    public ProportionsBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ProportionsBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        colorQueueInt.clear();
        colorQueueInt.addAll(colorsInt);

        //X coordinate of the last element
        float tempX = 0;
        //height of container view
        float h = getHeight();
        //width of container view
        float w = getWidth();
        //size of gaps (depends from container view width and denominator)
        float gapSize = (float) (getWidth() * this.gapSize / 100);
        //X coordinate of rounded edges, stands for radius of ark (depends from container view width and denominator)
        float circleCenterX = (float) getWidth() / 100;
        //arc radius
        float r = (float) (circleCenterX * curveOfEdges);

        //draw SEGMENTS based in the percent values proportions
        for (int k = 0; k < percentValueList.size(); k++) {
            if (k == 0) {
                //FIRST segment
                paint.setColor(getColorFromQueue());
                //draw arc
                if (showRoundEdges) {
                    drawArc(canvas, 0, 2 * r, h, 90);
                    tempX = r;
                }
                //draw rectangle
                drawRectangle(canvas, tempX, tempX + (w * percentValueList.get(k) / 100));
                tempX = tempX + (w * percentValueList.get(k) / 100);

            } else if (k == percentValueList.size() - 1) {
                //LAST segment
                //draw gap
                if (showGaps) {
                    drawGap(canvas, tempX, gapSize);
                    tempX += gapSize;
                }
                //draw rectangle
                paint.setColor(getColorFromQueue());
                drawRectangle(canvas, tempX, w - r);
                tempX = w - r;
                //draw arc
                if (showRoundEdges) {
                    drawArc(canvas, tempX - r, w, h, 270);
                } else {
                    drawRectangle(canvas, tempX, w);
                }
            } else {
                //MID segments
                //draw gap
                if (showGaps) {
                    drawGap(canvas, tempX, gapSize);
                    tempX += gapSize;
                }
                //draw rectangle
                paint.setColor(getColorFromQueue());
                drawRectangle(canvas, tempX, tempX + (w * percentValueList.get(k) / 100) + gapSize);
                tempX += (w * percentValueList.get(k) / 100) + gapSize;
            }
        }
    }

    private void drawRectangle(Canvas canvas, float start, float end) {
        canvas.drawRect(start, 0, end, getHeight(), paint);
    }

    private void drawArc(Canvas canvas, float start, float end, float h, int startAngle) {
        canvas.drawArc(start, 0, end, h, startAngle, 180, true, paint);
    }

    private void drawGap(Canvas canvas, float start, float gap) {
        paint.setColor(gapColor);
        canvas.drawRect(start, 0, start + gap, getHeight(), paint);
    }

    private ArrayList<Integer> getPercentValues(ArrayList<Integer> val) {
        int sum = 0;
        //get sum of all arguments
        for (int iterator : val) {
            sum += iterator;
        }

        ArrayList<Integer> percentValues = new ArrayList<>();
        //divide each element by sum to get % values
        for (int v = 0; v < val.size(); v++) {
            //check for minimalSegmentValue
            if ((val.get(v) * 100 / sum) != 0 && (val.get(v) * 100 / sum) < minimalSegmentValue) {
                percentValues.add(minimalSegmentValue);
            } else {
                percentValues.add(val.get(v) * 100 / sum);
            }
        }
        return percentValues;
    }

    //return Int value from queue of colors
    private Integer getColorFromQueue() {
        Integer temp = colorQueueInt.poll();
        colorQueueInt.offer(temp);
        return temp;
    }

    public void playAnimation() {
        //setup animations for proportionsBar4
        AnimatorSet animSet = new AnimatorSet();
        ObjectAnimator animIntList1 = ObjectAnimator
                .ofInt(this, "FirstSegment", 2, this.percentValueList.get(0));
        animIntList1.setDuration(animationDuration);
        ObjectAnimator animIntList2 = ObjectAnimator
                .ofInt(this, "SecondSegment", 2, this.percentValueList.get(1));
        animIntList2.setDuration(animationDuration);
        animSet.playTogether(animIntList1, animIntList2);
        animSet.start();
    }

    //setters are needed to animate custom view via external ObjectAnimator
    public void setFirstSegment(int i) {
        this.percentValueList.set(0, i);
        //redraw custom view on every argument change
        invalidate();
    }

    public void setSecondSegment(int j) {
        this.percentValueList.set(1, j);
        invalidate();
    }

    public void setThirdSegment(int k) {
        this.percentValueList.set(2, k);
        invalidate();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (animated && firstLaunch) playAnimation();
        firstLaunch = false;
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState ss = new SavedState(superState);

        ss.firstLaunch = this.firstLaunch;
        return ss;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (!(state instanceof SavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }

        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());

        this.firstLaunch = ss.firstLaunch;
    }

    //nested class for saving view's state
    static class SavedState extends BaseSavedState {
        boolean firstLaunch;

        public SavedState(Parcel in) {
            super(in);
            this.firstLaunch = in.readInt() == 1;
        }

        public SavedState(Parcelable superState) {
            super(superState);
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(firstLaunch ? 0 : 1);
        }

        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() {
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
    }

}
