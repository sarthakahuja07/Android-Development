package com.example.whatsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {
    ListView chatListView;
    ArrayList<String> messagesList;
    ArrayAdapter arrayAdapter;
    EditText chatText;
    String message;
    String username="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        chatListView=findViewById(R.id.chatListView);
        messagesList=new ArrayList<>();
        chatText=findViewById(R.id.chatText);
        arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,messagesList);
        chatListView.setAdapter(arrayAdapter);
        Intent intent=getIntent();
        username=intent.getStringExtra("username");
        Log.i("a",username.toString());
        ParseQuery<ParseObject> query1=new ParseQuery<ParseObject>("Messages");
        query1.whereEqualTo("sender",ParseUser.getCurrentUser().getUsername());
        query1.whereEqualTo("recipient",username);

        ParseQuery<ParseObject> query2=new ParseQuery<ParseObject>("Messages");
        query2.whereEqualTo("sender",username);
        query2.whereEqualTo("recipient",ParseUser.getCurrentUser().getUsername());

        ArrayList<ParseQuery<ParseObject>> queries =new ArrayList<>();
        queries.add(query1);
        queries.add(query2);

        ParseQuery<ParseObject> query3=ParseQuery.or(queries);
        query3.orderByAscending("CreatedAt");
        query3.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e==null){
                    if(objects.size()>0){
                        messagesList.clear();
                        for(ParseObject object : objects){
                            String content= object.getString("message");
                            if (object.getString("sender").equals(ParseUser.getCurrentUser().getUsername())){
                                messagesList.add(ParseUser.getCurrentUser().getUsername()+": "+content);
                            }else{
                                messagesList.add(username+": "+content);                            }
                        }
                        arrayAdapter.notifyDataSetChanged();
                    }
                }else {
                    e.printStackTrace();
                }
            }
        });
    }
    public void send(View view){
        message=chatText.getText().toString();
        InputMethodManager inputManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        Log.i("message",message);
        Log.i("sender",ParseUser.getCurrentUser().getUsername());
        Log.i("receiver",username);
        final ParseObject messages=new ParseObject("Messages");
        messages.put("recipient",username);
        messages.put("sender", ParseUser.getCurrentUser().getUsername());
        messages.put("message",message);
        messagesList.add(ParseUser.getCurrentUser().getUsername()+": "+chatText.getText().toString());
        arrayAdapter.notifyDataSetChanged();
        chatText.setText("");
        messages.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e==null){
                    Toast.makeText(ChatActivity.this, "Text Sent", Toast.LENGTH_SHORT).show();
                    Log.i("result","done");

                }else{
                    e.printStackTrace();
                }
            }
        });
    }

}
