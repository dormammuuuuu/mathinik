package com.santos_tech.math_inik;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class GameVersus extends AppCompatActivity {
    private Button[] p1_button = new Button[4];
    private Button[] p2_button = new Button[4];
    private String[] id;
    String ans_p1, ans_p2, decimal_p1, decimal_p2;
    double quotient_p1, quotient_p2;
    int p1_index, p2_index;
    TextView disp_num1, disp_num2, disp_num3, disp_num4, operator_p1, operator_p2, scoreboard_p1, scoreboard_p2;
    int temp, j = 0, num1_p1, num2_p1, num1_p2, num2_p2, answer_p1, answer_p2, operator_1, operator_2, score_p1, score_p2;
    ArrayList<Integer> answerRange = new ArrayList<Integer>();
    ArrayList<Integer> tempRange = new ArrayList<Integer>();

    private CountDownTimer time_p1, time_p2;
    long timeLong_p1 = 30000, timeLong_p2 = 30000;
    ProgressBar timer_p1, timer_p2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_versus);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int screenWidth = displaymetrics.widthPixels;
        int button_size = (int) (screenWidth * 0.17);

        disp_num1 = findViewById(R.id.display_num1);
        operator_p1 = findViewById(R.id.display_operator_p1);
        disp_num2 = findViewById(R.id.display_num2);
        disp_num3 = findViewById(R.id.display_num3);
        operator_p2 = findViewById(R.id.display_operator_p2);
        disp_num4 = findViewById(R.id.display_num4);
        scoreboard_p1 = findViewById(R.id.scoreboard_p1);
        scoreboard_p2 = findViewById(R.id.scoreboard_p2);
        timer_p1 = findViewById(R.id.timer_p1);
        timer_p2 = findViewById(R.id.timer_p2);
        timer_p1.getProgressDrawable().setColorFilter(Color.parseColor("#0E4292"), android.graphics.PorterDuff.Mode.SRC_IN);
        timer_p2.getProgressDrawable().setColorFilter(Color.parseColor("#FE4948"), android.graphics.PorterDuff.Mode.SRC_IN);

        id = new String[]{"p1_guess1", "p1_guess2", "p1_guess3", "p1_guess4", "p2_guess1", "p2_guess2", "p2_guess3", "p2_guess4"};
        for(int i=0; i<id.length; i++){
            temp = getResources().getIdentifier(id[i], "id", getPackageName());
            if (i > 3){
                p2_button[i - 4] = findViewById(temp);
                p2_button[i - 4].getLayoutParams().width = button_size;
                p2_button[i - 4].getLayoutParams().height = button_size;
            } else {
                p1_button[i] = findViewById(temp);
                p1_button[i].getLayoutParams().width = button_size;
                p1_button[i].getLayoutParams().height = button_size;
            }
        }

        for (int i = 0; i < 4; i++){
            int finalI = i;
            p1_button[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (p1_button[finalI].getText().toString().equals(ans_p1)){
                        p1_button[finalI].setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#5dff62"))); //correct
                        p1_button[finalI].setTextColor(ColorStateList.valueOf(Color.parseColor("#000000"))); //correct
                        score_p1++;
                        scoreboard_p1.setText("Score: " + score_p1);
                        time_p1.cancel();
                        timeLong_p1 += 1500;
                        StartTimer_playerOne();
                        time_p2.cancel();
                        timeLong_p1 -= 500;
                        StartTimer_playerTwo();
                    } else {
                        p1_button[p1_index].setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#5dff62")));//correct
                        p1_button[finalI].setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#f49089"))); //wrong
                        p1_button[finalI].setTextColor(ColorStateList.valueOf(Color.parseColor("#000000"))); //correct
                    }
                    p1_button[p1_index].setTextColor(ColorStateList.valueOf(Color.parseColor("#000000"))); //correct
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            generatePlayerOne();
                        }
                    }, 500);
                }
            });
        }

        for (int i = 0; i < 4; i++){
            int finalI = i;
            p2_button[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (p2_button[finalI].getText().toString().equals(ans_p2)){
                        p2_button[finalI].setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#5dff62"))); //correct
                        p2_button[finalI].setTextColor(ColorStateList.valueOf(Color.parseColor("#000000"))); //correct
                        score_p2++;
                        scoreboard_p2.setText("Score: " + score_p2);
                        time_p2.cancel();
                        timeLong_p2 += 1500;
                        StartTimer_playerTwo();
                        time_p1.cancel();
                        timeLong_p1 -= 500;
                        StartTimer_playerOne();
                    } else {
                        p2_button[p2_index].setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#5dff62")));//correct
                        p2_button[finalI].setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#f49089"))); //wrong
                        p2_button[finalI].setTextColor(ColorStateList.valueOf(Color.parseColor("#000000"))); //correct
                    }
                    p2_button[p2_index].setTextColor(ColorStateList.valueOf(Color.parseColor("#000000"))); //correct
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            generatePlayerTwo();
                        }
                    }, 500);
                }
            });
        }

        generatePlayerOne();
        StartTimer_playerOne();
        generatePlayerTwo();
        StartTimer_playerTwo();
    }

    private void StartTimer_playerOne(){
        timer_p1.setMax(30);
        time_p1 = new CountDownTimer(timeLong_p1, 1000) {

            public void onTick(long millisUntilFinished) {
                timer_p1.setProgress((int) (millisUntilFinished / 1000)); //Progressbar
                timeLong_p1 = millisUntilFinished;
            }

            public void onFinish() {
                time_p2.cancel();
                Intent soloIntent = new Intent(GameVersus.this, GameOver.class);
                soloIntent.putExtra("score_p1", score_p1);
                soloIntent.putExtra("score_p2", score_p2);
                soloIntent.putExtra("mode", "vs");
                GameVersus.this.startActivity(soloIntent);
                finish();
            }
        }.start();
    }

    private void StartTimer_playerTwo(){
        timer_p2.setMax(30);
        time_p2 = new CountDownTimer(timeLong_p2, 1000) {

            public void onTick(long millisUntilFinished) {
                timer_p2.setProgress((int) (millisUntilFinished / 1000)); //Progressbar
                timeLong_p2 = millisUntilFinished;
            }

            public void onFinish() {
                time_p1.cancel();
                Intent soloIntent = new Intent(GameVersus.this, GameOver.class);
                soloIntent.putExtra("score_p1", score_p1);
                soloIntent.putExtra("score_p2", score_p2);
                soloIntent.putExtra("mode", "vs");
                GameVersus.this.startActivity(soloIntent);
                finish();
            }
        }.start();
    }



    private void generatePlayerOne(){
        for (int i = 0; i < 4; i++) {
            p1_button[i].setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#0E4292")));
            p1_button[i].setTextColor(ColorStateList.valueOf(Color.parseColor("#ffffff"))); //correct
        }
        int temp = 0;
        Random randomGenerator = new Random();
        num1_p1 = randomGenerator.nextInt(100) + 1;
        num2_p1 = randomGenerator.nextInt(100) + 1 ;
        operator_1 = randomGenerator.nextInt(4);

        String op = "";
        switch (operator_1){
            case 0:
                op = "+";
                answer_p1 = num1_p1 + num2_p1;
                break;
            case 1:
                op = "-";
                answer_p1 = num1_p1 - num2_p1;
                break;
            case 2:
                op = "×";
                answer_p1 = num1_p1 * num2_p1;
                break;
            case 3:
                op = "÷";
                answer_p1 = num1_p1 / num2_p1;
                break;
        }
        ans_p1 = String.valueOf(answer_p1);
        if (operator_1 == 3){
            quotient_p1 = (double) num1_p1 / num2_p1;
            decimal_p1 = String.format("%.2f", quotient_p1);
            decimal_p1 = decimal_p1.substring(decimal_p1.indexOf("."));
            ans_p1 = ans_p1 + decimal_p1;
        }

        disp_num1.setText(String.valueOf(num1_p1));
        disp_num2.setText(String.valueOf(num2_p1));
        operator_p1.setText(op);
        displayNumbers(1);

    }

    private void generatePlayerTwo(){
        for (int i = 0; i < 4; i++) {
            p2_button[i].setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FE4948")));
            p2_button[i].setTextColor(ColorStateList.valueOf(Color.parseColor("#ffffff"))); //correct
        }
        int temp = 0;
        Random randomGenerator = new Random();
        num1_p2 = randomGenerator.nextInt(100) + 1;
        num2_p2 = randomGenerator.nextInt(100) + 1 ;
        operator_2 = randomGenerator.nextInt(4);

        String op = "";
        switch (operator_2){
            case 0:
                op = "+";
                answer_p2 = num1_p2 + num2_p2;
                break;
            case 1:
                op = "-";
                answer_p2 = num1_p2 - num2_p2;
                break;
            case 2:
                op = "×";
                answer_p2 = num1_p2 * num2_p2;
                break;
            case 3:
                op = "÷";
                answer_p2 = num1_p2 / num2_p2;
                break;
        }
        ans_p2 = String.valueOf(answer_p2);
        if (operator_2 == 3){
            quotient_p2 = (double) num1_p2 / num2_p2;
            decimal_p2 = String.format("%.2f", quotient_p2);
            decimal_p2 = decimal_p2.substring(decimal_p2.indexOf("."));
            ans_p2 = ans_p2 + decimal_p2;
        }

        disp_num3.setText(String.valueOf(num1_p2));
        disp_num4.setText(String.valueOf(num2_p2));
        operator_p2.setText(op);

        displayNumbers(2);

    }

    private void displayNumbers(int player){
        answerRange.clear();
        tempRange.clear();
        if (player == 1){
            int temp = (answer_p1 / 10) * 10;
            for(int i = temp; i < temp + 10; i++) {
                if (i == answer_p1){
                    continue;
                }
                tempRange.add(i);
            }
            Collections.shuffle(tempRange);
            for (int i = 0; i < 3; i++){
                answerRange.add(tempRange.get(i));
            }
            answerRange.add(answer_p1);
            Collections.shuffle(answerRange);
            p1_index = answerRange.indexOf(answer_p1);
            for (int i = 0; i < 4; i++){
                if (operator_1 == 3){
                    p1_button[i].setText(String.valueOf(answerRange.get(i)) + decimal_p1);
                } else {
                    p1_button[i].setText(String.valueOf(answerRange.get(i)));
                }
            }
        } else {
            if (player == 2){
                int temp = (answer_p2 / 10) * 10;
                for(int i = temp; i < temp + 10; i++) {
                    if (i == answer_p2){
                        continue;
                    }
                    tempRange.add(i);
                }
                Collections.shuffle(tempRange);
                for (int i = 0; i < 3; i++){
                    answerRange.add(tempRange.get(i));
                }
                answerRange.add(answer_p2);
                Collections.shuffle(answerRange);
                Log.d("ANS: ", String.valueOf(answer_p2));
                Log.d("answerRange:", answerRange.toString());
                p2_index = answerRange.indexOf(answer_p2);
                Log.d("S: ", answerRange.toString());
                for (int i = 0; i < 4; i++){
                    if (operator_2 == 3){
                        p2_button[i].setText(String.valueOf(answerRange.get(i)) + decimal_p2);
                    } else {
                        p2_button[i].setText(String.valueOf(answerRange.get(i)));
                    }
                }
            }
        }
    }
}