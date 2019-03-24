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
    //curve of edges of the custom view (bigger value for longer curves)
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
    //height of custom view in % of parent view
    private double scaleH = 100;

    private boolean firstLaunch = true;
    private boolean animated = false;
    private int animationDuration = 1000;

    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    public List<Float> percentValueList = new ArrayList<>();
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

    public ProportionsBar addValue(int value) {
        if (checkValue(value)) this.valueList.add(value);
        return this;
    }

    public ProportionsBar addValues(int... values) {
        for (int iterator : values) {
            if (checkValue(iterator)) this.valueList.add(iterator);
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

    public ProportionsBar heightOfBar(int h) {
        this.scaleH = h;
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
        initColorQueue();

        //width of container view
        float wBig = getWidth();
        //rounded edge radius
        float r = (float) (wBig * curveOfEdges / 100);
        //X coordinate of the last element
        float tempX = 0;
        //height of container view
        float h = (float) (getHeight() * scaleH / 100);

        float wSmall;
        if (!showRoundEdges) {
            wSmall = wBig;
        } else {
            wSmall = (float) (getWidth() - 2 * r - gapSize * (valueList.size() - 1));
        }
        //size of gaps (depends from container view width and denominator)
        float gapSize = (float) (wBig * this.gapSize / 100);

        //draw SEGMENTS based in the percent values proportions
        for (int k = 0; k < percentValueList.size(); k++) {
            if (k == 0) {
                //FIRST segment
                paint.setColor(getColorFromQueue());
                //draw rounded edge
                if (showRoundEdges) {
                    drawArc(canvas, 0, 2 * r, h, 90);
                    tempX = r;
                }
                //draw first rectangle
//                if(!(showRoundEdges && percentValueList.get(k) == minimalSegmentValue)) {
                drawRectangle(canvas, tempX - 1, tempX + (wSmall * percentValueList.get(k) / 100) - gapSize / 2, h);
                tempX += (wSmall * percentValueList.get(k) / 100) - gapSize / 2;
//                }

            } else if (k == percentValueList.size() - 1) {
                //LAST SEGMENT
                //draw gap
                if (showGaps) {
                    drawGap(canvas, tempX, gapSize, h);
                    tempX += gapSize;
                }
                if (showRoundEdges) {
                    //draw last rectangle
                    paint.setColor(getColorFromQueue());
                    drawRectangle(canvas, tempX, wBig - r + 1, h);
                    tempX = wBig - r;
                    //draw rounded edge
                    drawArc(canvas, tempX - r, wBig, h, 270);
                } else {
                    paint.setColor(getColorFromQueue());
                    drawRectangle(canvas, tempX, wSmall, h);
                    tempX = 0;
                }

            } else {
                //MID segments
                //draw gap
                if (showGaps) {
                    drawGap(canvas, tempX, gapSize, h);
                    tempX += gapSize;
                }
                //draw mid rectangle
                paint.setColor(getColorFromQueue());
                drawRectangle(canvas, tempX, tempX + (wSmall * percentValueList.get(k) / 100) - gapSize / 2, h);
                tempX += (wSmall * percentValueList.get(k) / 100) - gapSize / 2;
            }
        }
    }

    private void drawArc(Canvas canvas, float start, float end, float h, int startAngle) {
        canvas.drawArc(start, 0, end, h, startAngle, 180, true, paint);
    }

    private void drawRectangle(Canvas canvas, float start, float end, float h) {
        canvas.drawRect(start, 0, end, h, paint);
    }

    private void drawGap(Canvas canvas, float start, float gap, float h) {
        paint.setColor(gapColor);
        canvas.drawRect(start, 0, start + gap, h, paint);
    }

    //transform array of values into array of proportions ( % values )
    private ArrayList<Float> getPercentValues(ArrayList<Integer> val) {
        int sum = 0;
        //get sum of all arguments
        for (int iterator : val) {
            sum += iterator;
        }

        ArrayList<Float> percentValues = new ArrayList<>();
        //divide each element by sum to get % values
        for (int v = 0; v < val.size(); v++) {
            //check for minimalSegmentValue
            if ((val.get(v) * 100 / sum) > 0 && (val.get(v) * 100 / sum) < minimalSegmentValue) {
                percentValues.add((float) minimalSegmentValue);
            } else {
                percentValues.add((float) val.get(v) * 100 / sum);
            }
        }
        return percentValues;
    }

    private void initColorQueue() {
        colorQueueInt.clear();
        colorQueueInt.addAll(colorsInt);
    }

    //return Int value from queue of colors
    private Integer getColorFromQueue() {
        Integer temp = colorQueueInt.poll();
        //adds color back to queue
        colorQueueInt.offer(temp);
        return temp;
    }

    public boolean checkValue(int v) {
        if (v > 0) return true;
        else return false;
    }

    //currently only for percentValueList.size() = 3
    public void playAnimation() {
        //setup animations for proportionsBar4
        AnimatorSet animSet = new AnimatorSet();
        ObjectAnimator animIntList1 = ObjectAnimator
                .ofFloat(this, "FirstSegment", 0, this.percentValueList.get(0));
        animIntList1.setDuration(animationDuration);
        ObjectAnimator animIntList2 = ObjectAnimator
                .ofFloat(this, "SecondSegment", this.percentValueList.get(0), this.percentValueList.get(1));
        animIntList2.setDuration(animationDuration);
        animSet.playTogether(animIntList1, animIntList2);
        animSet.start();
    }

    //setters are needed to animate custom view via external ObjectAnimator
    public void setFirstSegment(float i) {
        this.percentValueList.set(0, i);
        //redraw custom view on every argument change
        invalidate();
    }

    public int setSecondSegment(float j) {
        this.percentValueList.set(1, j);
        return valueList.get(0);
    }

    public void setThirdSegment(float k) {
        this.percentValueList.set(2, k);
        invalidate();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        ArrayList<Float> k = getPercentValues(valueList);
        //fill percent list used for segment drawing
        for (int i = 0; i < valueList.size(); i++) {
            percentValueList.add(k.get(i));
        }

        //check for the first view launch animation
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
