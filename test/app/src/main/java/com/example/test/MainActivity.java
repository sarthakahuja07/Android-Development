package com.example.test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    Button start;
    TextView points;
    TextView ques;
    TextView timer;
    Button button0 ;
    Button button1 ;
    Button button2 ;
    Button button3 ;
    ArrayList<Integer> arrayList = new ArrayList<Integer>();
    int correctAns;
    TextView comment;
    int total=0;
    int correct=0;
    int a;
    int b;
    ConstraintLayout c;
    TextView score;


    public void nextQuestion(){
        Random rand=new Random();
        a= rand.nextInt(21);
        b=rand.nextInt(21);
        ques.setText(String.valueOf(a)+"+"+String.valueOf(b));
        arrayList.clear();

        correctAns=rand.nextInt(4);



        for (int i=0;i<4;i++){
            if (correctAns==i){
                arrayList.add(a+b);



            }else{

                arrayList.add(rand.nextInt(41));
            }
        }
        Log.i("hi",arrayList.toString());

        button0.setText(Integer.toString(arrayList.get(0)));
        button1.setText(Integer.toString(arrayList.get(1)));
        button2.setText(Integer.toString(arrayList.get(2)));
        button3.setText(Integer.toString(arrayList.get(3)));


    }


    public void start(View view) {

        start.setVisibility(View.INVISIBLE);
        points.setVisibility(View.VISIBLE);
        timer.setVisibility(View.VISIBLE);
        ques.setVisibility(View.VISIBLE);
        button0.setVisibility(View.VISIBLE);
        button1.setVisibility(View.VISIBLE);
        button2.setVisibility(View.VISIBLE);
        button3.setVisibility(View.VISIBLE);





        CountDownTimer countDownTimer= new CountDownTimer(30100,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if((millisUntilFinished/1000)<10){
                    timer.setText("00:0"+String.valueOf(millisUntilFinished/1000));
                }else {


                    timer.setText("00:" + String.valueOf(millisUntilFinished / 1000));
                }
            }

            @Override
            public void onFinish() {
                timer.setTextSize(22);
                timer.setText("Time \n Up");
                c.setVisibility(View.INVISIBLE);
                score.setText("YOU SCORED"+correct+"/"+total);
                score.setVisibility(View.VISIBLE);
                start.setVisibility(View.VISIBLE);









            }
        };
        countDownTimer.start();

    }
    public void input(View view) {

        comment.setVisibility(View.VISIBLE);
        if(Integer.toString(correctAns).equals(view.getTag().toString())){
            comment.setText("CORRECT");
            correct++;
            total++;

        }
        else{
            total++;
            comment.setText("  WRONG");
        }
        points.setText(String.valueOf(correct+"/"+total));
        nextQuestion();





    }












    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        start= findViewById(R.id.startButton);
        start.setVisibility(View.VISIBLE);
        points=findViewById(R.id.pointsTextView);
        points.setVisibility(View.INVISIBLE);
        ques= findViewById(R.id.quesTextView);
        ques.setVisibility(View.INVISIBLE);
        timer= findViewById(R.id.timerTextView);
        timer.setVisibility(View.INVISIBLE);

        button0=findViewById(R.id.ans0);
        button1=findViewById(R.id.ans1);
        button2=findViewById(R.id.ans2);
        button3=findViewById(R.id.ans3);
        button0.setVisibility(View.INVISIBLE);
        button1.setVisibility(View.INVISIBLE);
        button2.setVisibility(View.INVISIBLE);
        button3.setVisibility(View.INVISIBLE);
        comment=findViewById(R.id.commentTextView);
        comment.setVisibility(View.INVISIBLE);
        c= findViewById(R.id.constraintLayout);
        score=findViewById(R.id.textView5);
        nextQuestion();




    }
}
