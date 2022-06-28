package com.santos_tech.math_inik;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.widget.ProgressBar;
import android.os.Bundle;

public class GameTOF extends AppCompatActivity {
    ProgressBar timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_tof);
        getSupportActionBar().hide();
        timer = findViewById(R.id.timer);
        timer.getProgressDrawable().setColorFilter(Color.parseColor("#fff3efe9"), android.graphics.PorterDuff.Mode.SRC_IN);
    }
}