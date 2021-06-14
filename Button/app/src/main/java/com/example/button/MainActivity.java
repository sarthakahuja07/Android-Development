package com.example.button;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    Button start ;
    ArrayList<String> names=new ArrayList<String>();
    ImageView startImage;
    ImageView image;

    Button option0;
    Button option1;
    Button option2;
    Button option3;

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

    public void show(){
        String result=null;
        Download task=new Download();
        try {


            result = task.execute("https://www.forbes.com/celebrities/list/#tab:overall").get();


            Pattern p=Pattern.compile("class=\"exit_trigger_set\">(.*?)</a>");

            Matcher m = p.matcher(result);
            while (m.find()){
                names.add(m.group(1).replace("/n",""));
            }

        } catch (Exception e) {
            e.printStackTrace();

        }
        Log.i("tag",names.toString());






    }
    public void start(View view) {

        start.setVisibility(View.INVISIBLE);
        startImage.setVisibility(View.INVISIBLE);

        image.setVisibility(View.VISIBLE);
        option3.setVisibility(View.VISIBLE);
        option2.setVisibility(View.VISIBLE);
        option1.setVisibility(View.VISIBLE);
        option0.setVisibility(View.VISIBLE);
        show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        option0=findViewById(R.id.optionButton0);
        option1=findViewById(R.id.optionButton1);
        option2=findViewById(R.id.optionButton2);
        option3=findViewById(R.id.optionButton3);
        image=findViewById(R.id.imageView2);
        image.setVisibility(View.INVISIBLE);
        start=findViewById(R.id.button);
        startImage=findViewById(R.id.imageView);
        start.setVisibility(View.VISIBLE);
        startImage.setVisibility(View.VISIBLE);
        option3.setVisibility(View.INVISIBLE);
        option2.setVisibility(View.INVISIBLE);
        option1.setVisibility(View.INVISIBLE);
        option0.setVisibility(View.INVISIBLE);




    }
}
