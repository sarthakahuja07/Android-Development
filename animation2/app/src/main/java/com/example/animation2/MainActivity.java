package com.example.animation2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    public void fade(){



    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView image= (ImageView)findViewById(R.id.imageView);
        image.setX(-1000);
        image.animate().rotation(1800).setDuration(1000);
        image.animate().translationXBy(1000).setDuration(1000);
        image.animate().alpha(1).setDuration(2000);

    }
}
