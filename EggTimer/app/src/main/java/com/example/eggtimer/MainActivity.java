package com.example.eggtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Button button;
    Boolean buttonGo= true;
    SeekBar seekBar;
    CountDownTimer countDownTimer;
    MediaPlayer m;



    public void click(View view){
        button= (Button)findViewById(R.id.button2);


        if(buttonGo) {


            button.setText("STOP");
            seekBar.setEnabled(false);

            countDownTimer=new CountDownTimer(seekBar.getProgress()*1000+200,1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    seekBar.setProgress((int) millisUntilFinished/1000);


                }

                @Override
                public void onFinish() {
                    Log.i("Info","finish");
                    m = MediaPlayer.create(getApplicationContext(),R.raw.roses);
                    m.start();



                }
            };
            countDownTimer.start();


        buttonGo=false;
        }else{
            seekBar.setProgress(30);
            seekBar.setEnabled(true);
            countDownTimer.cancel();
            button.setText("GO!!!");
            buttonGo=true;
            m.stop();

        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        seekBar=findViewById(R.id.seekBar2);
        seekBar.setMax(300);
        seekBar.setProgress(30);
        final TextView textView=(TextView)findViewById(R.id.textView);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int min = progress/60;
                int sec = progress%60;

                if (min <10){
                    if (sec<10) {
                        textView.setText("0" + min + ":0" + sec);
                    }else{
                        textView.setText("0" + min + ":" + sec);
                    }
                }else if (sec<10){
                    textView.setText(min+":0"+sec);
                }else{

                    textView.setText(min+":"+sec);

                }


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });



    }
}
