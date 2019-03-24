package ru.startandroid.proportionbarlibrary;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import ru.startandroid.proportionsbarlibrary.ProportionsBar;

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
        proportionsBar1.setId(R.id.proportion_bar_1);
        proportionsBar1.showGaps(true)
                .gapSize(0.8)
                .heightOfBar(80)
                .showRoundEdges(true)
                .curveOfEdges(3.5)
                .minimalSegmentValue(1)
//                .animated(true)
//                .animationDuration(3000)
                .addIntColor(getResources().getColor(R.color.example1_1))
                .addIntColor(getResources().getColor(R.color.example1_2))
                .addIntColor(getResources().getColor(R.color.example1_3))
                .addValue(4)
                .addValue(37)
                .addValue(11)
                .addValue(4)
                .addValue(4)
                .addValue(4)
        ;

        LinearLayout container1 = findViewById(R.id.container_pb1);
        container1.addView(proportionsBar1);
    }

    private void setup2ndExample() {
        ProportionsBar proportionsBar2 = new ProportionsBar(this);
        proportionsBar2.setId(R.id.proportion_bar_2);
        proportionsBar2.showRoundEdges(false)
                .showGaps(false)
                .addIntColors(getResources().getColor(R.color.example2_1),
                        getResources().getColor(R.color.example2_2),
                        getResources().getColor(R.color.example2_3))
                .addValues(11, 30, 24, 12, 41, 27);

        LinearLayout container2 = findViewById(R.id.container_pb2);
        container2.addView(proportionsBar2);
    }

    private void setup3rdExample() {
        ProportionsBar proportionsBar3 = new ProportionsBar(this);
        proportionsBar3.setId(R.id.proportion_bar_3);
        proportionsBar3.showGaps(true)
                .showRoundEdges(true)
                .gapSize(0.3)
                .addIntColors(getResources().getColor(R.color.example3_1),
                        getResources().getColor(R.color.example3_2),
                        getResources().getColor(R.color.example3_3),
                        getResources().getColor(R.color.example3_4),
                        getResources().getColor(R.color.example3_5))
                .addValues(33, 2, 11, 40, 20);

        LinearLayout container3 = findViewById(R.id.container_pb3);
        container3.addView(proportionsBar3);
    }

    private void setup4thExample() {
        ProportionsBar proportionsBar4 = new ProportionsBar(this);
        proportionsBar4.setId(R.id.proportion_bar_4);
        proportionsBar4.showGaps(true)
                .gapSize(0.7)
                .animated(true)
                .animationDuration(2000)
                .gapColor(Color.BLACK)
                .showRoundEdges(false)
                .addIntColors(getResources().getColor(R.color.example4_1),
                        getResources().getColor(R.color.example4_2),
                        getResources().getColor(R.color.example4_3))
                .addValues(90, 333, 100);

        LinearLayout container4 = findViewById(R.id.container_pb4);
        container4.addView(proportionsBar4);
    }

    private void setup5thExample() {
        ProportionsBar proportionsBar5 = new ProportionsBar(this);
        proportionsBar5.setId(R.id.proportion_bar_5);
        proportionsBar5.showGaps(true)
                .gapSize(0.7)
                .animated(true)
                .animationDuration(2000)
                .gapColor(Color.BLACK)
                .showRoundEdges(false)
                .addIntColors(getResources().getColor(R.color.example4_1),
                        getResources().getColor(R.color.example4_2),
                        getResources().getColor(R.color.example4_3))
                .addValues(3300, 2200, 2100);

        LinearLayout container4 = findViewById(R.id.container_pb5);
        container4.addView(proportionsBar5);
    }
}
