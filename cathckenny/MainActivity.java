package com.hcs.cathckenny;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class MainActivity extends AppCompatActivity {
    Button btn;
    TextView score, time, bestscore;
    int score1;
    ImageView k0, k1, k2, k3, k4, k5, k6, k7, k8;
    ImageView imgs[];
    int a = ThreadLocalRandom.current().nextInt(0, 9);
    SharedPreferences sp;
    int bs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sp = this.getSharedPreferences("com.hcs.cathckenny", Context.MODE_PRIVATE);
        bs = sp.getInt("bestscore", 0);
        btn = findViewById(R.id.button);
        time = findViewById(R.id.textView2);
        score = findViewById(R.id.textView3);
        bestscore = findViewById(R.id.textView4);
        btn.setClickable(false);
        score1 = 0;
        setscorebest();
        setimage();
        sayac();
    }

    public void retry(View view){
        score1 = 0;
        btn.setClickable(false);
        setscorebest();
        setimage();
        sayac();
    }

    public void kenny(View view)
    {
        score1++;
        score.setText("SCORE : " + score1);
        imgs[a].setClickable(false);
        imgs[a].setVisibility(View.INVISIBLE);
        a = ThreadLocalRandom.current().nextInt(0, 9);
        imgs[a].setVisibility(View.VISIBLE);
        imgs[a].setClickable(true);
        if (score1 > bs)
            bestscore.setText("BEST SCORE : " + score1);
    }

    public void sayac(){

        new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long l) {
                time.setText("TIME : " + l / 1000);
            }

            @Override
            public void onFinish() {
                time.setText("TIME OVER");
                for (int i = 0; i < 9; i++) {
                    imgs[i].setVisibility(View.INVISIBLE);
                    imgs[i].setClickable(false);
                }

                if (score1 > a)
                    sp.edit().putInt("bestscore", score1).apply();
                btn.setClickable(true);
            }
        }.start();
    }

    public void setscorebest(){
        int b = sp.getInt("bestscore", 0);
        bestscore.setText("BEST SCORE : " + b);
    }

    public void setimage(){
        k0 = findViewById(R.id.imageView0);
        k1 = findViewById(R.id.imageView1);
        k2 = findViewById(R.id.imageView2);
        k3 = findViewById(R.id.imageView3);
        k4 = findViewById(R.id.imageView4);
        k5 = findViewById(R.id.imageView5);
        k6 = findViewById(R.id.imageView6);
        k7 = findViewById(R.id.imageView7);
        k8 = findViewById(R.id.imageView8);
        imgs = new ImageView[9];
        imgs[0] = k0;
        imgs[1] = k1;
        imgs[2] = k2;
        imgs[3] = k3;
        imgs[4] = k4;
        imgs[5] = k5;
        imgs[6] = k6;
        imgs[7] = k7;
        imgs[8] = k8;
        for (int i = 0; i < 9; i++) {
            imgs[i].setVisibility(View.INVISIBLE);
            imgs[i].setClickable(false);
        }
        imgs[a].setVisibility(View.VISIBLE);
        imgs[a].setClickable(true);
    }

}