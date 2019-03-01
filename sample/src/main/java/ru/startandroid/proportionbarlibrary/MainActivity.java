package ru.startandroid.proportionbarlibrary;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;

import java.util.Arrays;
import java.util.List;

import ru.startandroid.proportionsbarlibrary.ProportionsBar;
import ru.startandroid.proportionsbarlibrary.ProportionsBarBuilder;

public class MainActivity extends AppCompatActivity {
    private ProportionsBarBuilder builder1;
    private ProportionsBar proportionsBar1;
    private List<String> colorList1 = Arrays.asList(/*"#81DAF5",*/ "#008db9", "#1c0a63");

    private ProportionsBarBuilder builder2;
    private ProportionsBar proportionsBar2;
    private List<String> colorList2 = Arrays.asList("#fdd835", "#f57c00", "#bf360c");

    private ProportionsBarBuilder builder3;
    private ProportionsBar proportionsBar3;
    private List<String> colorList3 = Arrays.asList("#fdd835", "#f57c00", "#e53935", "#6d4c41", "#212121");

    private ProportionsBarBuilder builder4;
    private ProportionsBar proportionsBar4;
    private List<String> colorList4 = Arrays.asList("#ffcdd2", "#f48fb1", "#ba68c8");

    private LinearLayout container1;
    private LinearLayout container2;
    private LinearLayout container3;
    private LinearLayout container4;

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
        builder1 = new ProportionsBarBuilder(colorList1, true, true, 33, 22, 11);
        proportionsBar1 = new ProportionsBar(this, builder1);
        proportionsBar1.setGAP_SIZE(1.2);
        proportionsBar1.setCURVE(6.0);

        container1 = findViewById(R.id.proportionsBar1);
        container1.addView(proportionsBar1);
    }

    private void setup2ndExample() {
        builder2 = new ProportionsBarBuilder(colorList2, false, false, 33, 33, 33);
        proportionsBar2 = new ProportionsBar(this, builder2);

        container2 = findViewById(R.id.proportionsBar2);
        container2.addView(proportionsBar2);
    }

    private void setup3rdExample() {
        builder3 = new ProportionsBarBuilder(colorList3, true, false, 11, 22, 33, 44, 55);
        proportionsBar3 = new ProportionsBar(this, builder3);
        proportionsBar3.setGAP_SIZE(0.5);

        container3 = findViewById(R.id.proportionsBar3);
        container3.addView(proportionsBar3);
    }

    private void setup4thExample() {
        int ANIMATION_TIME = 4000;

        builder4 = new ProportionsBarBuilder(colorList4, true, true, 3, 2, 1);
        proportionsBar4 = new ProportionsBar(this, builder4);
        proportionsBar4.setGAP_SIZE(0.5);
        proportionsBar4.setGAP_COLOR("#212121");

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

        container4 = findViewById(R.id.proportionsBar4);
        container4.addView(proportionsBar4);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
