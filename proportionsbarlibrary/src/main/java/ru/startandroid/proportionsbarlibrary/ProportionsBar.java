package ru.startandroid.proportionsbarlibrary;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ComplexColorCompat;
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

    private boolean animated = false;
    private int animationTime = 1500;

    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int[] valueList = new int[0];
    public List<Integer> percentValueList = new ArrayList<>();
    //    private List<String> colorsString = new ArrayList<>();
    private List<Integer> colorsInt = new ArrayList<>();
    //    private Queue<String> colorQueue = new ArrayDeque<>();
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
        this.animationTime = duration;
        return this;
    }

    public ProportionsBar addValues(int... values) {
        this.valueList = values;
        int[] k = getPercentValues(valueList);
        //fill percent list for segment drawing
        for (int i = 0; i < valueList.length; i++) {
            percentValueList.add(k[i]);
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
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (animated) playAnimation();
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
        float circleCenterX = (getWidth() / 100);
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

    private int[] getPercentValues(int... val) {
        int sum = 0;
        //get sum of all arguments
        for (int iterator : val) {
            sum += iterator;
        }
        int[] percentValues = new int[val.length];
        //divide each element by sum to get % values
        for (int v = 0; v < val.length; v++) {
            //check for minimalSegmentValue
            if ((val[v] * 100 / sum) != 0 && (val[v] * 100 / sum) < minimalSegmentValue) {
                percentValues[v] = minimalSegmentValue;
            } else {
                percentValues[v] = val[v] * 100 / sum;
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
        animIntList1.setDuration(animationTime);
        ObjectAnimator animIntList2 = ObjectAnimator
                .ofInt(this, "SecondSegment", 2, this.percentValueList.get(1));
        animIntList2.setDuration(animationTime);
        animSet.playTogether(animIntList1, animIntList2);
        animSet.start();
    }

    // setters are needed to animate custom view via external ObjectAnimator
    public void setFirstSegment(int i) {
        this.percentValueList.set(0, i);
        //redraw custom view on every argument change
        invalidate();
    }

    public void setSecondSegment(int j) {
        this.percentValueList.set(1, j);
        //redraw custom view on every argument change
        invalidate();
    }

    public void setThirdSegment(int k) {
        this.percentValueList.set(2, k);
        //redraw custom view on every argument change
        invalidate();
    }
}
