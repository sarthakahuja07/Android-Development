package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    EditText city;
    TextView text;

    public class Downlaod extends AsyncTask<String, Void,String>{

        @Override
        protected String doInBackground(String... urls) {
            URL url ;
            HttpURLConnection httpURLConnection=null;
            String result="";
            try {
                url = new URL(urls[0]);
                httpURLConnection=(HttpURLConnection) url.openConnection();
                InputStream inputStream=httpURLConnection.getInputStream();
                InputStreamReader reader=new InputStreamReader(inputStream);
                int data=reader.read();
                while(data!=-1){
                    char current= (char)data;
                    result+=current;
                    data=reader.read();
                }
                return result;

            } catch (Exception e) {
                e.printStackTrace();
                Log.i("info","error in first");
                text.setText("Could,nt Find The City");
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject jsonObject=new JSONObject(s);
                String weather=jsonObject.getString("weather");
                JSONArray jsonArray=new JSONArray(weather);
                for (int i=0;i<jsonArray.length();i++){
                    JSONObject newJson=jsonArray.getJSONObject(i);
                    Log.i("info",newJson.getString("main"));
                    Log.i("info",newJson.getString("description"));
                    text.setText(newJson.getString("main")+" : "+newJson.getString("description")+"\r\n");





                }

            } catch (Exception e) {
                e.printStackTrace();
                text.setText("Could,nt Find The City");
                Log.i("info","error in post");

            }


        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        city=findViewById(R.id.editText);
        text=findViewById(R.id.textView2);


    }

    public void weather(View view){
        String result = null;
        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(city.getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
        city.getText().toString();
        Downlaod task = new Downlaod();

        try {
            result=task.execute("https://openweathermap.org/data/2.5/weather?q=" + city.getText().toString() + "&appid=439d4b804bc8187953eb36d2a8c26a02").get();

        } catch (Exception e) {
            e.printStackTrace();
            Log.i("info","error in object");
            text.setText("Could,nt Find The City");

        }
        Log.i("answer",result);
    }
}
