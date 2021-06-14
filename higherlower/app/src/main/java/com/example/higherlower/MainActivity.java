package com.example.higherlower;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    int random;

    public void guess(View view){
        EditText ans = (EditText)findViewById(R.id.editText);
        String ansstring= ans.getText().toString();
        int ansint=Integer.parseInt(ansstring);





        if (ansint> random){
            Toast.makeText(this, "Lower", Toast.LENGTH_SHORT).show();


        }
        else if (ansint == random){
            Toast.makeText(this, "You Found It", Toast.LENGTH_SHORT).show();
            Random rand = new Random();
            random = rand.nextInt(20);



        }else {
            Toast.makeText(this, "Higher", Toast.LENGTH_SHORT).show();

        }



    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Random rand= new Random();
        rand.nextInt(21);






    }
}
