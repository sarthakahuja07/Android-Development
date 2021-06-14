package com.example.tictactoe2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    //0=O 1=X 2=Null
    int active= 0;
    int[] set = {2,2,2,2,2,2,2,2,2};
    int[][] winnings={{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};
    boolean gameActive=true;


    public void appear(View view){
        ImageView image =(ImageView) view;
        int content=Integer.parseInt(image.getTag().toString());

        if(set[content]==2 && gameActive) {


            if (active == 0) {
                active = 1;
                image.setImageResource(R.drawable.oo);
                image.animate().alpha(1).setDuration(1000);
                set[content] = 0;


            } else if (active == 1) {
                active = 0;
                image.setImageResource(R.drawable.x);
                image.animate().alpha(1).setDuration(1000);
                set[content] = 1;


            }

        }
        for (int [] winning : winnings){
            if(set[winning[0]]==set[winning[1]] && set[winning[1]]==set[winning[2]] && set[winning[0]] != 2){


                String winner ="";

                if (active==0){
                    winner="X";
                    gameActive=false;
                }else{
                    winner="O";
                    gameActive=false;


                }

                TextView text =(TextView)findViewById(R.id.textView2);
                text.setText(winner + " wins");
                text.setVisibility(View.VISIBLE);
                Button button=(Button)findViewById(R.id.button);
                button.setVisibility(View.VISIBLE);

            }




        }

    }
    public void playAgain(View view){
        TextView text =(TextView)findViewById(R.id.textView2);

        text.setVisibility(View.INVISIBLE);
        Button button=(Button)findViewById(R.id.button);
        button.setVisibility(View.INVISIBLE);
        GridLayout grid  = findViewById(R.id.gridLayout);
        for(int i=0;i<grid.getChildCount();i++) {
            ImageView image = (ImageView) grid.getChildAt(i);
            image.setImageDrawable(null);

        }
        boolean gameActive=true;
        int active =0;
        int[][] winnings={{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};
        int[] set={};
        for(int i=0;i<9;i++){
            set[i]=2;
        }






    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
