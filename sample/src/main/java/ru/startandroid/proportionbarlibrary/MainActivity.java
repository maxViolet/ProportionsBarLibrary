package ru.startandroid.proportionbarlibrary;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;

import java.util.Arrays;
import java.util.List;

import ru.startandroid.proportionsbarlibrary.ProportionsBar;

public class MainActivity extends AppCompatActivity {
    private ProportionsBar proportionsBar1;
    private ProportionsBar proportionsBar2;
    private ProportionsBar proportionsBar3;
    private ProportionsBar proportionsBar4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setup1stExample();
        setup2ndExample();
        setup3rdExample();
        setup4thExample();
    }

    private void setup1stExample() {
        proportionsBar1 = new ProportionsBar(this);
        proportionsBar1.showGaps(true)
                .gapSize(1.2)
                .showRoundEdges(true)
                .curveOfEdges(7.5)
                .addIntColor(getResources().getColor(R.color.example1_1))
                .addIntColor(getResources().getColor(R.color.example1_2))
                .addIntColor(getResources().getColor(R.color.example1_3))
                .addValues(33, 22, 28);

        LinearLayout container1 = findViewById(R.id.proportionsBar1);
        container1.addView(proportionsBar1);
    }

    private void setup2ndExample() {
        proportionsBar2 = new ProportionsBar(this);
        proportionsBar2.showRoundEdges(false)
                .showGaps(false)
                .addIntColors(getResources().getColor(R.color.example2_1),
                        getResources().getColor(R.color.example2_2),
                        getResources().getColor(R.color.example2_3))
                .addValues(11, 30, 24, 12, 41, 27);

        LinearLayout container2 = findViewById(R.id.proportionsBar2);
        container2.addView(proportionsBar2);
    }

    private void setup3rdExample() {
        proportionsBar3 = new ProportionsBar(this);
        proportionsBar3.showGaps(true)
                .showRoundEdges(false)
                .gapSize(0.3)
                .addIntColors(getResources().getColor(R.color.example3_1),
                        getResources().getColor(R.color.example3_2),
                        getResources().getColor(R.color.example3_3),
                        getResources().getColor(R.color.example3_4),
                        getResources().getColor(R.color.example3_5))
                .addValues(33, 2, 11, 40, 20);

        LinearLayout container3 = findViewById(R.id.proportionsBar3);
        container3.addView(proportionsBar3);
    }

    private void setup4thExample() {
        int ANIMATION_TIME = 4000;

        proportionsBar4 = new ProportionsBar(this);
        proportionsBar4.showGaps(true)
                .gapSize(0.7)
                .gapColor(Color.BLACK)
                .showRoundEdges(true)
                .addIntColors(getResources().getColor(R.color.example4_1),
                        getResources().getColor(R.color.example4_2),
                        getResources().getColor(R.color.example4_3))
                .addValues(4276, 3238, 2820);

        //setup animations for proportionsBar4
        AnimatorSet animSet = new AnimatorSet();
        ObjectAnimator animIntList1 = ObjectAnimator
                .ofInt(proportionsBar4, "FirstSegment", 2, proportionsBar4.percentValueList.get(0));
        animIntList1.setDuration(ANIMATION_TIME);
        ObjectAnimator animIntList2 = ObjectAnimator
                .ofInt(proportionsBar4, "SecondSegment", 2, proportionsBar4.percentValueList.get(1));
        animIntList2.setDuration(ANIMATION_TIME);
        animSet.playTogether(animIntList1, animIntList2);
        animSet.start();

        LinearLayout container4 = findViewById(R.id.proportionsBar4);
        container4.addView(proportionsBar4);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
