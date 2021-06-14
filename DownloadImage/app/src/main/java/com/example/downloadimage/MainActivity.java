package com.example.downloadimage;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;


public class MainActivity extends AppCompatActivity {
    ImageView image;

    public void download(View view){

        Download task = new Download();

        try {
            Bitmap bitmap;

            bitmap = task.execute("https://img1.looper.com/img/gallery/was-jim-halpert-from-the-office-secretly-a-sociopath/intro-1565015060.jpg").get();
            image.setImageBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        image = findViewById(R.id.imageView3);
    }
    public class Download extends AsyncTask<String, Void , Bitmap>{


        @Override
        protected Bitmap doInBackground(String... urls) {
            try {
                URL url =new URL(urls[0]);
                HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
                urlConnection.connect();
                InputStream inputStream= urlConnection.getInputStream();
                Bitmap bitmap= BitmapFactory.decodeStream(inputStream);

                return bitmap;


            } catch (Exception e) {
                e.printStackTrace();
                Log.i("i","Failed");
            }
            return null;




        }
    }
}
