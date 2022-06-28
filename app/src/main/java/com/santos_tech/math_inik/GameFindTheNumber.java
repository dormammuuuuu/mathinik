package com.santos_tech.math_inik;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
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
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class GameFindTheNumber extends AppCompatActivity {
    ProgressBar timer;
    private String[] id;
    private TextView[] btn = new TextView[49];
    TextView scoreboard, randNum;
    int temp, num_find, score = 0, numIndex;
    long timeLong = 20000;
    LinearLayout pausebtn, pauseicon;
    MediaPlayer mp;
    boolean soundeffect;
    private CountDownTimer time;
    ArrayList<Integer> tmp = new ArrayList<Integer>();
    ArrayList<Integer> finalNum = new ArrayList<Integer>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_find_the_number);
        getSupportActionBar().hide();
        scoreboard = findViewById(R.id.scoreboard);
        pausebtn = findViewById(R.id.pausebtn);
        pauseicon = findViewById(R.id.pause_icon);
        timer = findViewById(R.id.timer);
        randNum = findViewById(R.id.randNumber);
        timer.getProgressDrawable().setColorFilter(Color.parseColor("#fff0dfdf"), android.graphics.PorterDuff.Mode.SRC_IN);

        SharedPreferences sh = getSharedPreferences("mathinik", MODE_PRIVATE);
        soundeffect = sh.getBoolean("soundeffect", true);

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int screenWidth = displaymetrics.widthPixels;
        int button_size = (int) (screenWidth * 0.12);

        id = new String[]{"btn1", "btn2", "btn3", "btn4", "btn5", "btn6", "btn7", "btn8", "btn9", "btn10", "btn11", "btn12", "btn13", "btn14", "btn15", "btn16", "btn17","btn18", "btn19", "btn20", "btn21", "btn22", "btn23", "btn24", "btn25", "btn26", "btn27", "btn28", "btn29", "btn30", "btn31", "btn32", "btn33", "btn34", "btn35", "btn36"};
        for(int i=0; i<id.length; i++){
            temp = getResources().getIdentifier(id[i], "id", getPackageName());
            btn[i] = (TextView)findViewById(temp);
            btn[i].getLayoutParams().width = button_size;
            btn[i].getLayoutParams().height = button_size;
            int finalI = i;
            btn[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for(int j = 0; j < 36; j++){
                        btn[j].setEnabled(false);
                    }
                    if (btn[finalI].getText().toString().equals(String.valueOf(num_find))){
                        btn[finalI].setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#5dff62")));
                        audioplay("correct");
                        score++;
                        scoreboard.setText("Score: " + score);
                        time.cancel();
                        timeLong += 1500;
                        StartTimer();
                    } else {
                        btn[numIndex].setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#5dff62")));
                        audioplay("wrong");
                        btn[finalI].setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#f49089")));
                    }

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            generateNumbers();
                        }
                    }, 500);
                }
            });
        }

        pausebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pauseicon.setBackgroundResource(R.drawable.ic_play);
                showPauseDialog();
            }
        });
        StartTimer();
        generateNumbers();
    }

    private void generateNumbers(){
        for(int j = 0; j < 36; j++){
            btn[j].setEnabled(true);
            btn[j].setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#fff0dfdf")));
        }
        tmp.clear();
        finalNum.clear();
        Random randomGenerator = new Random();
        num_find = randomGenerator.nextInt(100) + 1;

        for (int i = 0; i < 100; i++){
            if (i == num_find){
                continue;
            }
            tmp.add(i);
        }

        for (int i = 0; i < 35; i++){
            finalNum.add(tmp.get(i));
        }
        finalNum.add(num_find);
        Collections.shuffle(finalNum);

        for(int i=0; i < 36; i++){
            btn[i].setText(finalNum.get(i).toString());
        }
        numIndex = finalNum.indexOf(num_find);
        randNum.setText(String.valueOf(num_find));
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
                Intent soloIntent = new Intent(GameFindTheNumber.this, GameOver.class);
                soloIntent.putExtra("score", score);
                soloIntent.putExtra("mode", "find");
                GameFindTheNumber.this.startActivity(soloIntent);
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

    private void showPauseDialog(){
        time.cancel();
        int width = (int)(getResources().getDisplayMetrics().widthPixels*0.80);
        final Dialog dialog = new Dialog(GameFindTheNumber.this);
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
                Intent soloIntent = new Intent(GameFindTheNumber.this, MainMenu.class);
                GameFindTheNumber.this.startActivity(soloIntent);
                finish();
            }
        });

        dialog.getWindow().setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();
        dialog.setCancelable(false);

    }
}