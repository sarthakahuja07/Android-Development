package com.example.animation1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    boolean is1 = true;
    public void fade(View view){


        ImageView image1 = (ImageView)findViewById(R.id.imageView);
        ImageView image2 = (ImageView)findViewById(R.id.image2View);

        if( is1){
            is1=false;
            image1.animate().alpha(0).setDuration(50);
            image2.animate().alpha(1).setDuration(50);

        }else {
            is1=true;
            image2.animate().alpha(0).setDuration(50);
            image1.animate().alpha(1).setDuration(50);


        }






    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
