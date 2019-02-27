package ru.startandroid.proportionsbarlibrary;

import java.util.Arrays;
import java.util.List;

public class ProportionsBarBuilder {
    //draw round edges of view
    private boolean SHOW_ROUND_EDGES = true;
    //show GAPS
    private boolean SHOW_GAPS = true;
    //GAPs' color
    private String GAP_COLOR = "#ffffff";


    //minimal section value to be shown in % (meaning: values between >0% and <2% will be shown as 2% section)
    private static final int MINIMAL_SEGMENT_VALUE = 2;

    private int[] values;

    public ProportionsBarBuilder(List<String> colorList, boolean showGaps, boolean showRoundedEdges, int... values) {
        this.values = values;
        this.SHOW_GAPS = showGaps;
        this.SHOW_ROUND_EDGES = showRoundedEdges;

    }

    public void setSHOW_ROUND_EDGES(boolean SHOW_ROUND_EDGES) {
        this.SHOW_ROUND_EDGES = SHOW_ROUND_EDGES;
    }

    public void setSHOW_GAPS(boolean SHOW_GAPS) {
        this.SHOW_GAPS = SHOW_GAPS;
    }

    public void setGAP_COLOR(String GAP_COLOR) {
        this.GAP_COLOR = GAP_COLOR;
    }
}
