package com.example.timer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CountDownTimer c=new CountDownTimer(10000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                Log.i("info","Seconds left = "+millisUntilFinished/1000);


            }

            @Override
            public void onFinish() {
                Log.i("info","chl tbkc");

            }
        };
        c.start();


    }
}
