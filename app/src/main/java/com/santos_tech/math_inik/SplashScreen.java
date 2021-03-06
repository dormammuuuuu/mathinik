package com.santos_tech.math_inik;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashScreen extends AppCompatActivity {

    Intent Main;
    ImageView splash_icon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getSupportActionBar().hide();
        Main = new Intent(".MainMenu");
        splash_icon = findViewById(R.id.splash_icon);

        // Initialize animation
        Animation splash_anim = AnimationUtils.loadAnimation(this, R.anim.splash_anim);
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int screenWidth = displaymetrics.widthPixels;
        int width = (int) (screenWidth * 0.55); // 35% of screen.
        // Start top animation
        splash_icon.getLayoutParams().width = width;
        splash_icon.getLayoutParams().height = WRAP_CONTENT;
        splash_icon.setAnimation(splash_anim);
        
        Thread timer = new Thread(){
            public void run(){
                try{
                    sleep(3000);
                } catch (InterruptedException e){
                    e.printStackTrace();
                } finally {
                    startActivity(Main);
                    finish();
                }
            }
        };
        timer.start();
    }
}