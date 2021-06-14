package com.example.audio;

import androidx.appcompat.app.AppCompatActivity;

import android.icu.text.IDNA;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    MediaPlayer m ;
    AudioManager a;

    public void playy(View view){
        m.start();
    }
    public void pause(View view){
        m.pause();
    }





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        a=(AudioManager) getSystemService(AUDIO_SERVICE);
        m=MediaPlayer.create(this,R.raw.roses);
        SeekBar volume = findViewById(R.id.seekBar);
        int max= a.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int current= a.getStreamVolume(AudioManager.STREAM_MUSIC);
        volume.setMax(max);
        volume.setProgress(current);
        volume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                a.setStreamVolume(AudioManager.STREAM_MUSIC,progress,0);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        final SeekBar seek = (SeekBar)findViewById(R.id.seekBar2);
        seek.setMax(m.getDuration());
        seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                m.seekTo(progress);


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {


            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                seek.setProgress(m.getCurrentPosition());
            }
        },0,1000);
    }
}
