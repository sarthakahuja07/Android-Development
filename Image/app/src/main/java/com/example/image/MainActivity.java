package com.example.image;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    public void photo(View view){
        ImageView image= (ImageView) findViewById(R.id.imageView);
        image.setImageResource(R.drawable.postt);

    }

    public void backk(View view){
        ImageView image2 = (ImageView) findViewById(R.id.imageView);
        image2.setImageResource(R.drawable.pree);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
