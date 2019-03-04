package ru.startandroid.proportionsbarlibrary;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.IntegerRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class ProportionsBar extends View {
    //draw round edges
    private boolean showRoundEdges;
    //show GAPS
    private boolean showGaps;
    //GAPs' color
    private String gapColor;
    //minimal segment value to be shown in % of the bar width (meaning: values between >0% and <2% will be shown as 2% section)
    private int minimalSegmentValue;
    //GAPs' size in % of the container view's width
    private double gapSize = 1.0;
    //curve of the round edges of the custom view
    private double curve = 1.4;

    private Paint paint;
    public List<Integer> percentValueList = new ArrayList<>();
    private List<String> colors = new ArrayList<>();
    private Queue<String> colorQueue = new ArrayDeque<>();

    public ProportionsBar(@NonNull Context context, ProportionsBarBuilder barBuilder) {
        super(context);
        initValues(barBuilder);
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

    private void initValues(ProportionsBarBuilder barBuilder) {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        this.showRoundEdges = barBuilder.getSHOW_ROUND_EDGES();
        this.showGaps = barBuilder.getSHOW_GAPS();
        this.gapColor = barBuilder.getGAP_COLOR();
        this.minimalSegmentValue = barBuilder.getMINIMAL_SEGMENT_VALUE();

        //fill colorQueue with predefined colors from colorList
        colors.addAll(barBuilder.getColorList());

        //process the list to get proportions (each argument / sum)
        int[] k = getPercentValues(barBuilder.getValues());
        //final list for segment drawing
        for (int i = 0; i < (getPercentValues(barBuilder.getValues())).length; i++)
            percentValueList.add(k[i]);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        colorQueue.clear();
        colorQueue.addAll(colors);
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
        float r = (float) (circleCenterX * curve);

        //draw SEGMENTS based in the percent values proportions
        for (int k = 0; k < percentValueList.size(); k++) {
            if (k == 0) {
                //FIRST segment
                paint.setColor(Color.parseColor(getColor(colorQueue)));
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
                paint.setColor(Color.parseColor(getColor(colorQueue)));
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
                paint.setColor(Color.parseColor(getColor(colorQueue)));
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
        paint.setColor(Color.parseColor(gapColor));
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

    private String getColor(Queue<String> colors) {
        //return color and push it's value to array's tail
        String temp = colors.poll();
        colors.offer(temp);
        return temp;
    }

    public void setShowRoundEdges(boolean showRoundEdges) {
        this.showRoundEdges = showRoundEdges;
    }

    public void setShowGaps(boolean showGaps) {
        this.showGaps = showGaps;
    }

    public void setGapColor(String gapColor) {
        this.gapColor = gapColor;
    }

    public void setGapSize(double gapSize) {
        this.gapSize = gapSize;
    }

    public void setCurve(double curve) {
        this.curve = curve;
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
