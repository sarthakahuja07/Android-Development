package com.example.numbershape;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public void shape(View view){

        EditText nEditText = (EditText)findViewById(R.id.editText);
        int number = Integer.parseInt(nEditText.getText().toString());



        int allnumber=0;
        int square ;
        int i =0;
        int j=0;
        while( j<50){

            i++;
            allnumber=allnumber+i;
            square=i*i;



            if(number == square && number == allnumber){
                Toast.makeText(this, "BOTH", Toast.LENGTH_SHORT).show();
            }else if(number== square && number !=allnumber) {
                Toast.makeText(this, "Square", Toast.LENGTH_SHORT).show();

            }else if (number==allnumber && number != square) {
                Toast.makeText(this, "Triangular", Toast.LENGTH_SHORT).show();
            }
                j++;
        }


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
