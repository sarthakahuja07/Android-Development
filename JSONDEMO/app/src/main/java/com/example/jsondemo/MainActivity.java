package com.example.jsondemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    public class Download extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... urls) {
            URL url;
            String result="";
            HttpURLConnection httpURLConnection=null;
            try {
                url = new URL(urls[0]);
                httpURLConnection=(HttpURLConnection) url.openConnection();
                InputStream inputStream=httpURLConnection.getInputStream();
                InputStreamReader reader=new InputStreamReader(inputStream);
                int data=reader.read();
                while (data!=-1){
                    char content=(char)data;
                    result+=content;
                    data=reader.read();

                }
                return result;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }




        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject jsonObject=new JSONObject(s);
                String weather= jsonObject.getString("weather");
                Log.i("tag",weather);
                JSONArray jsonArray=new JSONArray(weather);
                for (int i=0;i<jsonArray.length();i++){
                    JSONObject jsonPart=jsonArray.getJSONObject(i);
                    Log.i("info",jsonPart.getString("main"));
                    Log.i("info",jsonPart.getString("description"));
                }

            } catch (Exception e) {
                e.printStackTrace();
                Log.i("tag","fail");
            }

        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String result=null;

        Download task=new Download();
        try {
            task.execute("https://samples.openweathermap.org/data/2.5/weather?lat=35&lon=139&appid=439d4b804bc8187953eb36d2a8c26a02").get();

        } catch (Exception e) {
            e.printStackTrace();
            Log.i("tag","yha error h");
        }

    }
}
