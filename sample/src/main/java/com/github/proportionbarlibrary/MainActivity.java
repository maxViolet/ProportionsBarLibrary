package com.github.proportionbarlibrary;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import com.github.proportionsbarlibrary.ProportionsBar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setup1stExample();
        setup2ndExample();
        setup3rdExample();
        setup4thExample();
        setup5thExample();
    }

    private void setup1stExample() {
        ProportionsBar proportionsBar1 = new ProportionsBar(this);
        proportionsBar1.setId(R.id.proportions_bar_1);
        proportionsBar1.showGaps(true)
                .gapSize(0.8)
                .heightOfBar(80)
                .showRoundedCorners(true)
                .radiusRoundedCorners(3.5)
                .minimalSegmentValue(1)
                .addColors("#81DAF5", "#008db9", "#1c0a63")
                .addValues(4.0, 37, 11, 3.9, -4.89, 4);

        LinearLayout container1 = findViewById(R.id.container_pb1);
        container1.addView(proportionsBar1);
    }

    private void setup2ndExample() {
        ProportionsBar proportionsBar2 = new ProportionsBar(this);
        proportionsBar2.setId(R.id.proportions_bar_2);
        proportionsBar2.showRoundedCorners(false)
                .showGaps(false)
                .addColors(getResources().getColor(R.color.example2_1),
                        getResources().getColor(R.color.example2_2),
                        getResources().getColor(R.color.example2_3))
                .addValues(11.801, -30.05, 24.79, 12.001, 41.238, 27.11);

        LinearLayout container2 = findViewById(R.id.container_pb2);
        container2.addView(proportionsBar2);
    }

    private void setup3rdExample() {
        long a = 22;
        short b = 3;
        int c = -10;
        byte d = 9;
        float e = -20;

        ProportionsBar proportionsBar3 = new ProportionsBar(this);
        proportionsBar3.setId(R.id.proportions_bar_3);
        proportionsBar3.showGaps(true)
                .showRoundedCorners(true)
                .gapSize(0.3)
                .addColors(getResources().getColor(R.color.example3_1),
                        getResources().getColor(R.color.example3_2),
                        getResources().getColor(R.color.example3_3),
                        getResources().getColor(R.color.example3_4),
                        getResources().getColor(R.color.example3_5))
                .addValues(a, b, c, d, e);

        LinearLayout container3 = findViewById(R.id.container_pb3);
        container3.addView(proportionsBar3);
    }

    private void setup4thExample() {
        ProportionsBar proportionsBar4 = new ProportionsBar(this);
        proportionsBar4.setId(R.id.proportions_bar_4);
        proportionsBar4.showGaps(true)
                .gapSize(0.7)
                .animated(true)
                .animationDuration(2000)
                .gapColor(Color.BLACK)
                .showRoundedCorners(false)
                .addColors(getResources().getColor(R.color.example4_1),
                        getResources().getColor(R.color.example4_2),
                        getResources().getColor(R.color.example4_3))
                .addValues(90, 333, 100);

        LinearLayout container4 = findViewById(R.id.container_pb4);
        container4.addView(proportionsBar4);
    }

    private void setup5thExample() {
        ProportionsBar proportionsBar5 = new ProportionsBar(this);
        proportionsBar5.setId(R.id.proportions_bar_5);
        proportionsBar5.showGaps(true)
                .gapSize(0.7)
                .animated(true)
                .animationDuration(2000)
                .gapColor(Color.BLACK)
                .showRoundedCorners(false)
                .addColors(getResources().getColor(R.color.example4_1),
                        getResources().getColor(R.color.example4_2),
                        getResources().getColor(R.color.example4_3))
                .addValues(3325, 2199, 2137);

        LinearLayout container4 = findViewById(R.id.container_pb5);
        container4.addView(proportionsBar5);
    }
}
