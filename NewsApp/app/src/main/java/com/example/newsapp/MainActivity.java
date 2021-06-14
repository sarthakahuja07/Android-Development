package com.example.newsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.net.UrlQuerySanitizer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<String> title;
    ArrayList<String>urlArray;
    ListView listView;
    ArrayAdapter arrayAdapter;
    SQLiteDatabase sqLiteDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sqLiteDatabase=this.openOrCreateDatabase("website",MODE_PRIVATE,null);
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS news(id INTEGER PRIMARY KEY ,articleId INTEGER, title VARCHAR, url VARCHAR)");
        title=new ArrayList<String>();
        urlArray=new ArrayList<String>();
        listView=findViewById(R.id.listView);
        arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,title);
        listView.setAdapter(arrayAdapter);
        DownloadTask task=new DownloadTask();
        task.execute("https://hacker-news.firebaseio.com/v0/topstories.json?print=pretty");
        update();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getApplicationContext(),Main2Activity.class);
                intent.putExtra("url",urlArray.get(position));
                startActivity(intent);
            }
        });


    }

    public void update(){
        Cursor c =sqLiteDatabase.rawQuery("SELECT * FROM news",null);
        int titleIndex=c.getColumnIndex("title");
        int urlIndex=c.getColumnIndex("url");

        if(c.moveToFirst()){
            title.clear();
            urlArray.clear();

            do {
                title.add(c.getString(titleIndex));
                urlArray.add(c.getString(urlIndex));
            }while (c.moveToNext());
            arrayAdapter.notifyDataSetChanged();
        }
    }
    public class DownloadTask extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... urls) {
            URL url;
            String result="";
            HttpURLConnection urlConnection=null;
            try {
                url = new URL(urls[0]);
                urlConnection= (HttpURLConnection) url.openConnection();
                InputStream inputStream=urlConnection.getInputStream();
                InputStreamReader inputStreamReader=new InputStreamReader(inputStream);
                int data=inputStreamReader.read();
                while(data!=-1){
                    char current=(char)data;
                    result+=current;
                    data=inputStreamReader.read();
                }
                JSONArray jsonArray=new JSONArray(result);
                int number=30;
                Log.i("length", String.valueOf(jsonArray.length()));
                if(jsonArray.length()<30){
                    number=jsonArray.length();
                }
                Log.i("result",result);
                sqLiteDatabase.execSQL("DELETE FROM news");
                for(int i=0;i<number;i++){
                    String newsId=jsonArray.getString(i);

                    url= new URL("https://hacker-news.firebaseio.com/v0/item/"+newsId+".json?print=pretty");
                    urlConnection= (HttpURLConnection) url.openConnection();
                    inputStream=urlConnection.getInputStream();
                    inputStreamReader=new InputStreamReader(inputStream);
                    data=inputStreamReader.read();
                    String articleInfo="";
                    while(data!=-1){
                        char current=(char)data;
                        articleInfo+=current;
                        data=inputStreamReader.read();
                    }
                    JSONObject jsonObject=new JSONObject(articleInfo);
                    if(!jsonObject.isNull("title")&&!jsonObject.isNull("url")) {
                        String title = jsonObject.getString("title");
                        Log.i("title",title);
                        String url2 = jsonObject.getString("url");
                        Log.i("url",url2);
                        String sql = "INSERT INTO news(articleId,title,url) VALUES(?, ?, ?)";
                        SQLiteStatement statement = sqLiteDatabase.compileStatement(sql);
                        statement.bindString(1, newsId);
                        statement.bindString(2, String.valueOf(title));
                        statement.bindString(3, url2);
                        statement.execute();
                    }
                }
                return result;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            update();
        }
    }
}
