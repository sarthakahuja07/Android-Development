package com.example.twitter2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class FeedActivity extends AppCompatActivity {
    ListView feedView;
    SimpleAdapter simpleAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        feedView =findViewById(R.id.FeedView);
        final List<Map<String,String>> TweetData = new ArrayList<>();

         simpleAdapter=new SimpleAdapter(this,TweetData,android.R.layout.simple_list_item_2,new String[] {"username","content"},new int[] {android.R.id.text1,android.R.id.text2});
        feedView.setAdapter(simpleAdapter);

        ParseQuery<ParseObject> query =new ParseQuery<ParseObject>("Tweet");
        query.whereContainedIn("username", ParseUser.getCurrentUser().getList("following"));
        query.orderByDescending("createdAt");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e==null && objects!=null){
                    for(ParseObject object : objects ){
                        Map<String,String> TweetInfo =new HashMap<>();
                        TweetInfo.put("username",object.getList("username").toString().replace("[","").replace("]",""));
                        TweetInfo.put("content",object.getList("tweet").toString().replace("[","").replace("]",""));
                        TweetData.add(TweetInfo);
                    }
                    simpleAdapter=new SimpleAdapter(FeedActivity.this,TweetData,android.R.layout.simple_list_item_2,new String[] {"username","content"},new int[] {android.R.id.text1,android.R.id.text2});
                    feedView.setAdapter(simpleAdapter);

                }
            }
        });

    }
}
