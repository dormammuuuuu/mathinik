package com.santos_tech.math_inik;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;
import android.os.Bundle;

public class GameOver extends AppCompatActivity {
    int score, highscore, score_p1, score_p2;
    TextView user_score, high_score;
    Button menu_over, restart_over;
    ImageView gameover_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        getSupportActionBar().hide();

        user_score = findViewById(R.id.user_score);
        high_score = findViewById(R.id.high_score);
        menu_over = findViewById(R.id.menu_over);
        restart_over = findViewById(R.id.restart_over);
        gameover_img = findViewById(R.id.gameover_img);

        Intent intent = getIntent();
        String mode = intent.getStringExtra("mode");

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int screenWidth = displaymetrics.widthPixels;
        int button_width = (int) (screenWidth * 0.30); // 15% of screen.
        restart_over.getLayoutParams().width = button_width;
        menu_over.getLayoutParams().width = button_width;

        if (!mode.equals("vs")){
            if(mode.equals("solo")){
                restart_over.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#ffc0dfdc")));
                menu_over.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#ffc0dfdc")));
                gameover_img.setColorFilter(Color.parseColor("#ffc0dfdc"));
            } else if (mode.equals("tof")){
                restart_over.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#fff3efe9")));
                menu_over.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#fff3efe9")));
                gameover_img.setColorFilter(Color.parseColor("#fff3efe9"));
            } else if(mode.equals("find")) {
                restart_over.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#fff0dfdf")));
                menu_over.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#fff0dfdf")));
                gameover_img.setColorFilter(Color.parseColor("#fff0dfdf"));
            }
            score = intent.getIntExtra("score", 0);
            loadHighScore(mode);
            if (score > highscore){
                savescore(mode);
                highscore = score;
            }
            user_score.setText("Your score: " + score);
            high_score.setText("High Score: " + highscore);
        } else {
            restart_over.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#ffe6dff1")));
            menu_over.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#ffe6dff1")));
            gameover_img.setColorFilter(Color.parseColor("#ffe6dff1"));
            score_p1 = intent.getIntExtra("score_p1", 0);
            score_p2 = intent.getIntExtra("score_p2", 0);
            user_score.setText("Player 1: " + score_p1);
            high_score.setText("Player 2: " + score_p2);
        }


        restart_over.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loadIntent;
                if (mode.equals("tof")){
                    loadIntent = new Intent(GameOver.this, GameTOF.class);
                } else if (mode.equals("find")) {
                    loadIntent = new Intent(GameOver.this, GameFindTheNumber.class);
                } else if (mode.equals("solo")){
                    loadIntent = new Intent(GameOver.this, GameSolo.class);
                } else {
                    loadIntent = new Intent(GameOver.this, GameVersus.class);
                }
                GameOver.this.startActivity(loadIntent);
                finish();
            }
        });

        menu_over.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loadIntent = new Intent(GameOver.this, MainMenu.class);
                GameOver.this.startActivity(loadIntent);
                finish();
            }
        });

    }

    private void loadHighScore(String mode){
        SharedPreferences app_data = getSharedPreferences("mathinik", MODE_PRIVATE);
        if (mode.equals("tof")){
            highscore = app_data.getInt("tof_highscore", 0);
        } else if (mode.equals("find")) {
            highscore = app_data.getInt("find_highscore", 0);
        } else {
            highscore = app_data.getInt("highscore", 0);
        }
    }

    private void savescore(String mode){
        SharedPreferences app_preferences = getSharedPreferences("mathinik",MODE_PRIVATE);
        SharedPreferences.Editor app_data = app_preferences.edit();
        if (mode.equals("tof")){
            app_data.putInt("tof_highscore", score);
            app_data.commit();
        } else if (mode.equals("tof")){
            app_data.putInt("find_highscore", score);
            app_data.commit();
        } else {
            app_data.putInt("highscore", score);
            app_data.commit();
        }
    }
}