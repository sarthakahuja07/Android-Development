/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.example.twitter2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.LogInCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class MainActivity extends AppCompatActivity {
    EditText username;
    EditText password;
    Button signUpButton;
    TextView text;
    boolean isSignup=true;
    public void changeToUser(){
        Intent intent=new Intent(getApplicationContext(),UserActivity.class);
        startActivity(intent);
    }


    public void signup(View view){
        if(isSignup){
            Log.i("hi","hello");
            ParseUser user=new ParseUser();
            user.setUsername(username.getText().toString());
            user.setPassword(password.getText().toString());
            user.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(ParseException e) {
                    if(e==null){
                        Log.i("status","Success");
                        Toast.makeText(MainActivity.this, "Signed up as "+ParseUser.getCurrentUser().getUsername(), Toast.LENGTH_SHORT).show();
                        changeToUser();
                    }else{
                        e.printStackTrace();
                    }
                }
            });
        }else if(! isSignup){
            ParseUser.logInInBackground(username.getText().toString(), password.getText().toString(), new LogInCallback() {
                @Override
                public void done(ParseUser user, ParseException e) {
                    if(e==null){
                        Log.i("login status","Success");
                        Toast.makeText(MainActivity.this, "Logged In as "+ParseUser.getCurrentUser().getUsername(), Toast.LENGTH_SHORT).show();
                        changeToUser();
                    }else{
                        e.printStackTrace();
                    }
                }
            });
        }
    }
    public void change(View view){
        if(isSignup){
            signUpButton.setText("Login");
            text.setText("Or, Signup");
            isSignup=false;
        }
        else{
            signUpButton.setText("Signup");
            text.setText("Or, Login");
            isSignup=true;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = findViewById(R.id.usernameEditText);
        password = findViewById(R.id.passwordEditText);
        signUpButton = findViewById(R.id.signupButton);
        text= findViewById(R.id.textView);
        if(ParseUser.getCurrentUser()!=null) {
            changeToUser();
        }


        ParseAnalytics.trackAppOpenedInBackground(getIntent());
    }

}