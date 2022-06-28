package com.santos_tech.math_inik;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.LinearLayout;
import android.os.Bundle;

public class MainMenu extends AppCompatActivity {
    ImageView menu_logo;
    TextView label;
    LinearLayout solo, versus, tof, identification;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        getSupportActionBar().hide();
        menu_logo = findViewById(R.id.menu_logo);
        label = findViewById(R.id.label);
        solo = findViewById(R.id.solo_btn);
        versus = findViewById(R.id.versus_btn);
        tof = findViewById(R.id.tof_btn);
        identification = findViewById(R.id.identification_btn);

        Animation grow = AnimationUtils.loadAnimation(this, R.anim.grow);
        Animation menu_icon_anim = AnimationUtils.loadAnimation(this, R.anim.morph_icon);
        menu_logo.setAnimation(menu_icon_anim);

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int screenWidth = displaymetrics.widthPixels;
        int button_width = (int) (screenWidth * 0.35); // 35% of screen.
        int button_height = button_width;

        solo.getLayoutParams().height = button_height;
        solo.getLayoutParams().width = button_width;
        versus.getLayoutParams().height = button_height;
        versus.getLayoutParams().width = button_width;
        tof.getLayoutParams().height = button_height;
        tof.getLayoutParams().width = button_width;
        identification.getLayoutParams().height = button_height;
        identification.getLayoutParams().width = button_width;

        solo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent soloIntent = new Intent(MainMenu.this, GameSolo.class);
                MainMenu.this.startActivity(soloIntent);
                finish();
            }
        });

        tof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent tofIntent = new Intent(MainMenu.this, GameTOF.class);
                MainMenu.this.startActivity(tofIntent);
                finish();
            }
        });

        menu_icon_anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                label.setVisibility(View.INVISIBLE);
                solo.setVisibility(View.INVISIBLE);
                versus.setVisibility(View.INVISIBLE);
                tof.setVisibility(View.INVISIBLE);
                identification.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                label.setAnimation(grow);
                label.setVisibility(View.VISIBLE);
                animateButtons();
            }

            @Override public void onAnimationRepeat(Animation animation) { }
        });
    }

    private void animateButtons(){
        int[] layoutIDs = {R.id.solo_btn, R.id.versus_btn, R.id.tof_btn, R.id.identification_btn};
        int i = 1;

        for (int viewId : layoutIDs) {
            Animation btn_animate = AnimationUtils.loadAnimation(this, R.anim.home_btn_anim);
            btn_animate.setStartOffset(i * 200);

            int layout_tmp = layoutIDs[i-1];
            LinearLayout layout = (LinearLayout) findViewById(layout_tmp);
            layout.startAnimation(btn_animate);
            layout.setVisibility(View.VISIBLE);
            i ++;
        }
    }
}