package com.hcs.numguess;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private EditText edText;
    private Button button;
    private TextView textvi;
    public int hak = 3;
    public int mynum;
    public int usernum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edText = (EditText)findViewById(R.id.edtext);
        button = (Button)findViewById(R.id.button);
        textvi = (TextView)findViewById(R.id.textvi);
        mynum = new Random().nextInt(10);
        System.out.println(mynum);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(edText.getText().toString())) {
                    usernum = Integer.valueOf(edText.getText().toString());
                    if (hak > 0){
                        if (usernum == mynum)
                            textvi.setText("Tebrikler Doğru Bildiniz...");
                        else {
                            textvi.setText("Tekrar Deneyiniz...");
                            hak--;
                            textvi.setText("KALAN HAKKINIZ : " + hak);
                        }
                    }
                    else {
                        edText.setEnabled(false);
                        button.setEnabled(false);
                    }
                }
            }
        });
    }
}