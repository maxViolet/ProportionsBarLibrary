package ru.startandroid.proportionbarlibrary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;

import ru.startandroid.proportionsbarlibrary.ProportionsBar;
import ru.startandroid.proportionsbarlibrary.ProportionsBarBuilder;

public class MainActivity extends AppCompatActivity {
    private ProportionsBar proportionsBar1;
    private ProportionsBar proportionsBar2;
    private LinearLayout container1;
    private LinearLayout container2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



//        proportionsBar1 = new ProportionsBar(this, new ProportionsBarBuilder());
        container1 = findViewById(R.id.proportionsBar1);
        proportionsBar1.setCOLOR1("#ffd54f");
        proportionsBar1.setCOLOR2("#ef6c00");
        proportionsBar1.setCOLOR3("#bf360c");

        container1.addView(proportionsBar1);

        proportionsBar2 = new ProportionsBar(this, 3323,223,321);
        proportionsBar2.setSHOW_GAPS(false);
        proportionsBar2.setSHOW_ROUND_EDGES(false);

        container2 = findViewById(R.id.proportionsBar2);
        container2.addView(proportionsBar2);
    }
}
