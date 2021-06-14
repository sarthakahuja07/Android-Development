package com.example.parse3;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.parse.ParseUser;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.parse.LogInCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.SignUpCallback;


public class MainActivity extends AppCompatActivity implements View.OnKeyListener  {

    public  void changeToUser(){
        Intent intent = new Intent(getApplicationContext(),User_activity.class);
        startActivity(intent);
    }
    boolean isSignUp = true;
    EditText username;
    EditText password;
    ParseUser user;
    Button button;
    TextView textView;
    ConstraintLayout layout;

    public void switches(View view){
        if(button.getText().equals("Sign Up")){
            isSignUp=false;
            button.setText("Login");
            textView.setText("Or,Sign up");
        }else{
            isSignUp=true;
            button.setText("Sign Up");
            textView.setText("Or,Login");

        }
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager inputMethodManager=(InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(),0);
            }
        });

    }

    public void signup(View view){
        if(isSignUp){//signup  true
            if(username.getText().toString().matches("")|| password.getText().toString().matches("")){
                Toast.makeText(this, "Enter username and password", Toast.LENGTH_SHORT).show();
            }else{
                user=new ParseUser();
                user.setUsername(String.valueOf(username.getText()));
                user.setPassword(String.valueOf(password.getText()));
                user.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e==null){
                            Log.i("result","success");
                            changeToUser();
                        }else{
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }else{//login false
            if(username.getText().toString().matches("")|| password.getText().toString().matches("")){
                Toast.makeText(this, "Enter username and password", Toast.LENGTH_SHORT).show();
            }else{
                ParseUser.logInInBackground(String.valueOf(username.getText()), String.valueOf(password.getText()), new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if(e==null){
                            Toast.makeText(MainActivity.this, "Logged in as "+ParseUser.getCurrentUser().getUsername(), Toast.LENGTH_SHORT).show();
                            changeToUser();
                        }else{
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("Instagram");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username=findViewById(R.id.usernameText);
        password=findViewById(R.id.passText);
        button=findViewById(R.id.button);
        textView=findViewById(R.id.textView1);
        layout=findViewById(R.id.layout);
        password.setOnKeyListener(this);
        if(ParseUser.getCurrentUser()!=null){
            if(ParseUser.getCurrentUser().getUsername()!=null){
                changeToUser();
            }

        }
        ParseAnalytics.trackAppOpenedInBackground(getIntent());
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_ENTER && event.getAction()==KeyEvent.ACTION_DOWN){
            signup(v);
        }
        return false;
    }
}