package com.santos_tech.math_inik;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;
import android.os.Bundle;

public class GameOver extends AppCompatActivity {
    int score, highscore;
    TextView user_score, high_score;
    Button menu_over, restart_over;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        getSupportActionBar().hide();

        user_score = findViewById(R.id.user_score);
        high_score = findViewById(R.id.high_score);
        menu_over = findViewById(R.id.menu_over);
        restart_over = findViewById(R.id.restart_over);

        Intent intent = getIntent();
        score = intent.getIntExtra("score", 0);
        String mode = intent.getStringExtra("mode");

        loadHighScore(mode);

        if (score > highscore){
            savescore(mode);
            highscore = score;
        }

        user_score.setText("Your score: " + score);
        high_score.setText("High Score: " + highscore);

        restart_over.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loadIntent;
                if (mode.equals("tof")){
                    loadIntent = new Intent(GameOver.this, GameTOF.class);
                } else if (mode.equals("find")) {
                    loadIntent = new Intent(GameOver.this, GameFindTheNumber.class);
                } else {
                    loadIntent = new Intent(GameOver.this, GameSolo.class);
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