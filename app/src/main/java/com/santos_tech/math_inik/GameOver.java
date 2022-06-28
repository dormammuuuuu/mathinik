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

        loadHighScore();
        if (score > highscore){
            savescore();
            highscore = score;
        }

        user_score.setText("Your score: " + score);
        high_score.setText("High Score: " + highscore);

        restart_over.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent soloIntent = new Intent(GameOver.this, GameSolo.class);
                GameOver.this.startActivity(soloIntent);
                finish();
            }
        });

        menu_over.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent soloIntent = new Intent(GameOver.this, MainMenu.class);
                GameOver.this.startActivity(soloIntent);
                finish();
            }
        });

    }

    private void loadHighScore(){
        SharedPreferences app_data = getSharedPreferences("mathinik", MODE_PRIVATE);
        highscore = app_data.getInt("highscore", 0);
    }

    private void savescore(){
        SharedPreferences app_preferences = getSharedPreferences("mathinik",MODE_PRIVATE);
        SharedPreferences.Editor app_data = app_preferences.edit();
        app_data.putInt("highscore", score);
        app_data.commit();
    }
}