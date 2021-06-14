package com.example.video;



import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

import com.example.video.R;

public class Main2Activity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        VideoView video= (VideoView)findViewById(R.id.videoView);
        video.setVideoPath("android.resource://"+getPackageName()+"/"+ R.raw.tootsieroll);
        MediaController m = new MediaController(this);
        m.setAnchorView(video);
        video.setMediaController(m);
        video.start();

    }

}
