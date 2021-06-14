package com.example.currencyconverter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public void convert(View view){

        EditText dollar= (EditText)findViewById(R.id.dollarEditText);
        String dollarstring = dollar.getText().toString();
        double dollardouble = Double.parseDouble(dollarstring);
        double rupeesdouble= dollardouble*76.8;
        String rupeesstring= Double.toString(rupeesdouble);
        Toast.makeText(this, "$ " + dollarstring +" = ₹ "+ rupeesstring, Toast.LENGTH_LONG).show();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
