package com.santos_tech.math_inik;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.os.Bundle;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.LinearLayout;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class GameSolo extends AppCompatActivity {
    ProgressBar timer;
    TextView display_num1, display_operator, display_num2, scoreboard;
    LinearLayout pausebtn, pauseicon;
    private TextView[] choices = new TextView[8];
    private String[] id;
    int num1, num2, operator, answer, temp, score = 0;
    long timeLong = 20000;
    String ans;
    ArrayList<Integer> answerRange = new ArrayList<Integer>();
    ArrayList<Integer> tempRange = new ArrayList<Integer>();
    MediaPlayer mp;
    boolean soundeffect;
    private CountDownTimer time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_solo);
        getSupportActionBar().hide();

        SharedPreferences sh = getSharedPreferences("mathinik", MODE_PRIVATE);
        soundeffect = sh.getBoolean("soundeffect", true);

        id = new String[]{"guess1", "guess2", "guess3", "guess4", "guess5", "guess6", "guess7", "guess8"};
        for(int i=0; i<id.length; i++){
            temp = getResources().getIdentifier(id[i], "id", getPackageName());
            choices[i] = (TextView)findViewById(temp);
        }

        timer = findViewById(R.id.timer);
        display_num1 = findViewById(R.id.display_num1);
        display_operator = findViewById(R.id.display_operator);
        display_num2 = findViewById(R.id.display_num2);
        scoreboard = findViewById(R.id.scoreboard);
        pausebtn = findViewById(R.id.pausebtn);
        pauseicon = findViewById(R.id.pause_icon);

        timer.getProgressDrawable().setColorFilter(Color.parseColor("#ffc0dfdc"), android.graphics.PorterDuff.Mode.SRC_IN);
        randomizer();
        choicesController();
        StartTimer();

        pausebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pauseicon.setBackgroundResource(R.drawable.ic_play);
                showPauseDialog();
            }
        });
    }

    //7.5s timer function
    private void StartTimer() {
        timer.setMax(20);
        time = new CountDownTimer(timeLong, 1000) {

            public void onTick(long millisUntilFinished) {
                timer.setProgress((int) (millisUntilFinished / 1000)); //Progressbar
                timeLong = millisUntilFinished;
            }

            public void onFinish() {
                Intent soloIntent = new Intent(GameSolo.this, GameOver.class);
                soloIntent.putExtra("score", score);
                GameSolo.this.startActivity(soloIntent);
                finish();
            }
        }.start();
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

    private void choicesController(){
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int screenWidth = displaymetrics.widthPixels;
        int button_width = (int) (screenWidth * 0.17); // 15% of screen.
        for (int i = 0; i < 8; i++){
            int finalI = i;
            choices[i].getLayoutParams().height = button_width;
            choices[i].getLayoutParams().width = button_width;
            choices[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for(int i = 0; i < 8; i++){
                        choices[i].setEnabled(false);
                    }
                    if (choices[finalI].getText().equals(ans)){
                        audioplay("correct");
                        choices[finalI].setBackground(getResources().getDrawable(R.drawable.ic_solo_guess_bg_correct));
                        score++;
                        scoreboard.setText("Score: " + score);
                        time.cancel();
                        timeLong += 3000;
                        StartTimer();
                    } else {
                        audioplay("wrong");
                        choices[finalI].setBackground(getResources().getDrawable(R.drawable.ic_solo_guess_bg_wrong));
                        for (int i = 0; i < 8; i++){
                            if(choices[i].getText().toString().contains(ans)){
                                choices[i].setBackground(getResources().getDrawable(R.drawable.ic_solo_guess_bg_correct));
                            }
                        }
                    }


                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            randomizer();
                        }
                    }, 500);
                }
            });
        }
    }

    private void displayChoices(){
        ans = String.valueOf(answer);
        answerRange.clear();
        tempRange.clear();
        for(int i = 0; i < 8; i++){
            choices[i].setEnabled(true);
            choices[i].setBackground(getResources().getDrawable(R.drawable.ic_solo_guess_bg));
        }
        int baseNum = answer / 10;
        String decimal = "";
        for (int i = baseNum * 10; i < (baseNum * 10) + 20; i++){
            if (i == answer){
                continue;
            }
            tempRange.add(i);
        }
        Log.d("temprange", tempRange.toString());
        if (operator == 3){
            double quotient = (double) num1 / num2;
            decimal = String.format("%.2f", quotient);
            decimal = decimal.substring(decimal.indexOf("."));
            ans = ans + decimal;
        }
        for (int i = 0; i < 7; i++){
            answerRange.add(tempRange.get(i));
        }
        answerRange.add(answer);
        Collections.shuffle(answerRange);
        for (int i = 0; i < 8; i++){
            if (operator == 3) {
                choices[i].setText(answerRange.get(i).toString() + decimal);
            } else {
                choices[i].setText(answerRange.get(i).toString());
            }
        }
        Log.d("Answer", ans);
    }

    private void randomizer(){
        int temp = 0;
        Random randomGenerator = new Random();
        num1 = randomGenerator.nextInt(100) + 1;
        num2 = randomGenerator.nextInt(100) + 1 ;
        operator = randomGenerator.nextInt(4);
        Log.d("Q:", String.valueOf(num1) + " " + String.valueOf(operator) + " " + String.valueOf(num2));

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

        displayChoices();
    }

    private void showPauseDialog(){
        time.cancel();
        int width = (int)(getResources().getDisplayMetrics().widthPixels*0.80);
        final Dialog dialog = new Dialog(GameSolo.this);
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
                Intent soloIntent = new Intent(GameSolo.this, MainMenu.class);
                GameSolo.this.startActivity(soloIntent);
                finish();
            }
        });

        dialog.getWindow().setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();
        dialog.setCancelable(false);

    }

}