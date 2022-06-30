package com.santos_tech.math_inik;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.os.Bundle;
import android.widget.Switch;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class GameTOF extends AppCompatActivity {
    ProgressBar timer;
    TextView display_num1, display_operator, display_num2, display_ans, scoreboard;
    LinearLayout pausebtn, pauseicon;
    Button truebtn, falsebtn;
    int num1, num2, operator, answer, score = 0;
    long timeLong = 20000;
    String ans, randomans;
    ArrayList<Integer> answerRange = new ArrayList<Integer>();
    ArrayList<Integer> tempRange = new ArrayList<Integer>();
    MediaPlayer mp;
    boolean soundeffect;
    private CountDownTimer time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_tof);
        getSupportActionBar().hide();

        timer = findViewById(R.id.timer);
        display_num1 = findViewById(R.id.display_num1);
        display_operator = findViewById(R.id.display_operator_p1);
        display_num2 = findViewById(R.id.display_num2);
        display_ans = findViewById(R.id.display_ans);
        scoreboard = findViewById(R.id.scoreboard);
        pausebtn = findViewById(R.id.pausebtn);
        pauseicon = findViewById(R.id.pause_icon);
        truebtn = findViewById(R.id.true_button);
        falsebtn = findViewById(R.id.false_button);

        timer.getProgressDrawable().setColorFilter(Color.parseColor("#fff3efe9"), android.graphics.PorterDuff.Mode.SRC_IN);

        SharedPreferences sh = getSharedPreferences("mathinik", MODE_PRIVATE);
        soundeffect = sh.getBoolean("soundeffect", true);

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int screenWidth = displaymetrics.widthPixels;
        int button_width = (int) (screenWidth * 0.20); // 15% of screen.
        truebtn.getLayoutParams().width = button_width;
        falsebtn.getLayoutParams().width = button_width;

        truebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifyAnswer(true);
            }
        });

        falsebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifyAnswer(false);
            }
        });

        pausebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pauseicon.setBackgroundResource(R.drawable.ic_play);
                showPauseDialog();
            }
        });

        randomizer();
        StartTimer();
    }

    private void verifyAnswer(boolean userchoice){
        boolean compare = ans.equals(randomans);
        if (userchoice == compare){
            if (userchoice == true){
                truebtn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#5dff62")));
            } else {
                falsebtn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#5dff62")));
            }
            score++;
            scoreboard.setText("Score: " + String.valueOf(score));
            audioplay("correct");
        } else {
            if (userchoice == true){
                truebtn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#f49089")));
                falsebtn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#5dff62")));
            } else {
                truebtn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#5dff62")));
                falsebtn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#f49089")));
            }
            audioplay("wrong");
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                randomizer();
            }
        }, 500);
    }

    private void audioplay(String type){
        if (type.equals("correct")){
            mp = MediaPlayer.create(this, R.raw.correct);
        } else {
            mp = MediaPlayer.create(this, R.raw.wrong);
        }

        if (soundeffect == false){
            mp.setVolume(0,0);
        } else {
            mp.setVolume(1,1);
        }

        if (mp.isPlaying()){
            mp.stop();
            mp.reset();
            mp.release();
        } else {
            mp.start();
        }

        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                mp.stop();
                mp.reset();
                mp.release();
            }
        });
    }

    private void selectRandomAnswer(){
        String decimal = "";
        answerRange.clear();
        tempRange.clear();
        ans = String.valueOf(answer);
        int tmp = answer / 10;
        for (int i = tmp * 10; i < (tmp * 10) + 20; i++){
            if(i == answer){
                continue;
            }
            tempRange.add(i);
        }

        Collections.shuffle(tempRange);
        answerRange.add(tempRange.get(0));
        answerRange.add(answer);
        Collections.shuffle(answerRange);
        randomans = String.valueOf(answerRange.get(0));
        if (operator == 3){
            double quotient = (double) num1 / num2;
            decimal = String.format("%.2f", quotient);
            decimal = decimal.substring(decimal.indexOf("."));
            randomans = randomans + decimal;
        }
    }

    private void randomizer(){
        int temp = 0;
        truebtn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#fff3efe9")));
        falsebtn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#fff3efe9")));
        Random randomGenerator = new Random();
        num1 = randomGenerator.nextInt(100) + 1;
        num2 = randomGenerator.nextInt(100) + 1 ;
        operator = randomGenerator.nextInt(4);

        String op = "";
        switch (operator){
            case 0:
                op = "+";
                answer = num1 + num2;
                break;
            case 1:
                op = "-";
                answer = num1 - num2;
                break;
            case 2:
                op = "×";
                answer = num1 * num2;
                break;
            case 3:
                op = "÷";
                answer = num1 / num2;
                break;
        }

        display_num1.setText(String.valueOf(num1));
        display_num2.setText(String.valueOf(num2));
        display_operator.setText(op);

        selectRandomAnswer();

        display_ans.setText(randomans);

    }

    private void StartTimer() {
        timer.setMax(20);
        time = new CountDownTimer(timeLong, 1000) {

            public void onTick(long millisUntilFinished) {
                timer.setProgress((int) (millisUntilFinished / 1000)); //Progressbar
                timeLong = millisUntilFinished;
            }

            public void onFinish() {
                Intent tofIntent = new Intent(GameTOF.this, GameOver.class);
                tofIntent.putExtra("score", score);
                tofIntent.putExtra("mode", "tof");
                GameTOF.this.startActivity(tofIntent);
                finish();
            }
        }.start();
    }

    private void showPauseDialog(){
        time.cancel();
        int width = (int)(getResources().getDisplayMetrics().widthPixels*0.80);
        final Dialog dialog = new Dialog(GameTOF.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.pause_dialog);
        dialog.dismiss();
        final Switch sound_effects = dialog.findViewById(R.id.sound_effects_switch);
        sound_effects.setChecked(soundeffect);
        Button resume_game = dialog.findViewById(R.id.resume_btn);
        Button return_menu = dialog.findViewById(R.id.menu_btn);

        resume_game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StartTimer();
                pauseicon.setBackgroundResource(R.drawable.ic_pause);
                dialog.dismiss();
            }
        });

        sound_effects.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                soundeffect = (isChecked == true) ? true : false;

                // Storing data into SharedPreferences
                SharedPreferences app_preferences = getSharedPreferences("mathinik",MODE_PRIVATE);
                // Creating an Editor object to edit(write to the file)
                SharedPreferences.Editor app_data = app_preferences.edit();
                // Storing the key and its value as the data fetched from edittext
                app_data.putBoolean("soundeffect", soundeffect);
                app_data.commit();
            }
        });

        return_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent soloIntent = new Intent(GameTOF.this, MainMenu.class);
                GameTOF.this.startActivity(soloIntent);
                finish();
            }
        });

        dialog.getWindow().setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();
        dialog.setCancelable(false);

    }
}