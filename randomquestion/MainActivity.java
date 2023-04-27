package com.hcs.randomquestion;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private int s1, s2;
    private ToggleButton pl, mn, mt, div;
    private Button ta, nq;
    private TextView no, nt;
    private EditText ans;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //RandomNumbers
        s1 = new Random().nextInt(100);
        s2 = new Random().nextInt(100);

        //Operators
        pl = findViewById(R.id.plus);
        mn = findViewById(R.id.minus);
        mt = findViewById(R.id.multiply);
        div = findViewById(R.id.divide);
        //Buttons
        ta = findViewById(R.id.tryanswer);
        nq = findViewById(R.id.nextquestion);
        //TextView
        no = findViewById(R.id.numberone);
        nt = findViewById(R.id.numbertwo);
        //write numbers to screen
        no.setText(String.valueOf(s1));
        nt.setText(String.valueOf(s2));
        //EditText
        ans = findViewById(R.id.answer);

        nq.setEnabled(false);
        pl.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    pl.setChecked(false);
                    //One time user can choose the operators.I don't want to changed
                    pl.setEnabled(false);
                    mn.setEnabled(false);
                    mt.setEnabled(false);
                    div.setEnabled(false);

                    ta.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (Integer.valueOf(ans.getText().toString()) == s1 + s2)
                                nq.setEnabled(true);
                        }
                    });//end of ta
                }
            }
        });//end of plus

        mn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    mn.setChecked(false);
                    //One time user can choose the operators.I don't want to changed
                    pl.setEnabled(false);
                    mn.setEnabled(false);
                    mt.setEnabled(false);
                    div.setEnabled(false);

                    ta.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (Integer.valueOf(ans.getText().toString()) == s1 - s2)
                                nq.setEnabled(true);
                        }
                    });//end of ta
                }
            }
        });//end of minus

        mt.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    mt.setChecked(false);
                    //One time user can choose the operators.I don't want to changed
                    pl.setEnabled(false);
                    mn.setEnabled(false);
                    mt.setEnabled(false);
                    div.setEnabled(false);

                    ta.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (Integer.valueOf(ans.getText().toString()) == s1 * s2)
                                nq.setEnabled(true);
                        }
                    });//end of ta
                }
            }
        });//end of multiply

        div.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    div.setChecked(false);
                    //One time user can choose the operators.I don't want to changed
                    pl.setEnabled(false);
                    mn.setEnabled(false);
                    mt.setEnabled(false);
                    div.setEnabled(false);

                    ta.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (Integer.valueOf(ans.getText().toString()) == s1 / s2)
                                nq.setEnabled(true);
                        }
                    });//end of ta
                }
            }
        });//end of divide

        nq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pl.setEnabled(true);
                mn.setEnabled(true);
                mt.setEnabled(true);
                div.setEnabled(true);
                s1 = new Random().nextInt(1000);
                s2 = new Random().nextInt(1000);
                no.setText(String.valueOf(s1));
                nt.setText(String.valueOf(s2));
                nq.setEnabled(false);
            }
        });
    }
}