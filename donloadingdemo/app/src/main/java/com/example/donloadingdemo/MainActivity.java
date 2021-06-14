package com.example.donloadingdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    public class Download extends AsyncTask<String, Void,String> {


        @Override
        protected String doInBackground(String... urls) {
            String result = "";
            URL url  ;

            HttpURLConnection urlConnection= null;
            try {
                url=new URL(urls[0]);
                urlConnection = (HttpURLConnection)url.openConnection();

                InputStream inputStream = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(inputStream);

                int data=reader.read();

                while (data!=-1){
                    char content=(char)data;
                    result+=content;
                    data=reader.read();



                }

                return result;

            } catch (Exception e) {
                e.printStackTrace();
                return "failed";
            }




        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Download task = new Download();
        String result= null;
        try {




            result= task.execute("https://zappycode.com/").get();


        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.i("i",result);


    }
}
