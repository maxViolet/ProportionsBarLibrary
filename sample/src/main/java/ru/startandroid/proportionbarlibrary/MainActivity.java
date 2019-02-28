package ru.startandroid.proportionbarlibrary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;

import java.util.Arrays;
import java.util.List;

import ru.startandroid.proportionsbarlibrary.ProportionsBar;
import ru.startandroid.proportionsbarlibrary.ProportionsBarBuilder;

public class MainActivity extends AppCompatActivity {
    private ProportionsBar proportionsBar1;
    private ProportionsBarBuilder builder1;
    private List<String> colorList1 = Arrays.asList("#81DAF5", "#008db9", "#1c0a63");

    private ProportionsBar proportionsBar2;
    private ProportionsBarBuilder builder2;
    private List<String> colorList2 = Arrays.asList("#fdd835", "#f57c00", "#bf360c");

    private ProportionsBar proportionsBar3;
    private ProportionsBarBuilder builder3;
    private List<String> colorList3 = Arrays.asList("#fdd835", "#f57c00", "#bf360c", "#bf360c");

    private LinearLayout container1;
    private LinearLayout container2;
    private LinearLayout container3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        builder1 = new ProportionsBarBuilder(colorList1, true, true, 222, 232, 44);
//        proportionsBar1 = new ProportionsBar(this, builder1);
//        container1 = findViewById(R.id.proportionsBar1);
//        container1.addView(proportionsBar1);
//
//        builder2 = new ProportionsBarBuilder(colorList2, false, false, 3, 2, 1);
//        proportionsBar2 = new ProportionsBar(this, builder2);
//        container2 = findViewById(R.id.proportionsBar2);
//        container2.addView(proportionsBar2);

        builder3 = new ProportionsBarBuilder(colorList3, false, false, 3, 2, 1, 7, 4);
        proportionsBar3 = new ProportionsBar(this, builder3);
        container3 = findViewById(R.id.proportionsBar3);
        container3.addView(proportionsBar3);


    }
}
