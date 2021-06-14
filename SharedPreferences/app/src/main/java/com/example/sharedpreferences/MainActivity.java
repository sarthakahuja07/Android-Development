package com.example.sharedpreferences;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SharedMemory;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sharedPreferences=this.getSharedPreferences("package com.example.sharedpreferences", Context.MODE_PRIVATE);
        ArrayList<String>names=new ArrayList<String>();
        names.add("sarthak");
        names.add("hi");
        names.add("yoyo");

        try {
            sharedPreferences.edit().putString("names",ObjectSerializer.serialize(names)).apply();


        } catch (Exception e) {
            e.printStackTrace();
        }
        ArrayList<String>hello=new ArrayList<>();
        try {
            hello=(ArrayList)ObjectSerializer.deserialize(sharedPreferences.getString("names",ObjectSerializer.serialize(new ArrayList<>())));
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.i("hahahahahha",hello.toString());

    }
}
