package com.example.numbertable;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    SeekBar seekBar;
    final ArrayList<Integer> ar = new ArrayList<Integer>();


    public void table(int number,ArrayList a){


        for (int i=1;i<100;i++){
            a.add(number*i);

        }
        ArrayAdapter<Integer> adapter= new ArrayAdapter<Integer>(this,android.R.layout.simple_expandable_list_item_1,a);
        listView.setAdapter(adapter);

    }



    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seekBar= findViewById(R.id.seekBar);

        listView=findViewById(R.id.listView);
        final int pro= 10;
        seekBar.setMax(20);
        seekBar.setProgress(pro);
        seekBar.setMin(1);
        table(10,ar);


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                final ArrayList<Integer> a = new ArrayList<Integer>();



                table(progress,a);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Toast.makeText(MainActivity.this, "YOU CLICKED"+a.get(position), Toast.LENGTH_SHORT).show();
                    }
                });






            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }
}
