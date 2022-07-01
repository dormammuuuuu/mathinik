package com.santos_tech.math_inik;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.LinearLayout;
import android.os.Bundle;

public class MainMenu extends AppCompatActivity {
    ImageView menu_logo, image1, image2, image3, image4;
    TextView label;
    LinearLayout solo, versus, tof, identification;
    private TextView[] buttonLabel = new TextView[4];
    private String[] id;
    int temp;
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
        image1 = findViewById(R.id.imageView2);
        image2 = findViewById(R.id.imageView3);
        image3 = findViewById(R.id.imageView4);
        image4 = findViewById(R.id.imageView5);

        id = new String[]{"textView2", "textView3", "textView4", "textView5"};
        for(int i=0; i<id.length; i++){
            temp = getResources().getIdentifier(id[i], "id", getPackageName());
            buttonLabel[i] = findViewById(temp);
            buttonLabel[i].setTextSize(TypedValue.COMPLEX_UNIT_PX, 20);
        }

        Animation grow = AnimationUtils.loadAnimation(this, R.anim.grow);
        Animation menu_icon_anim = AnimationUtils.loadAnimation(this, R.anim.morph_icon);
        menu_logo.setAnimation(menu_icon_anim);

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int screenWidth = displaymetrics.widthPixels;
        int button_width = (int) (screenWidth * 0.32); // 35% of screen.
        int button_height = button_width;

        int logo_width = (int) (screenWidth * 0.65);
        int logo_height = (int) (screenWidth * 0.15);
        menu_logo.getLayoutParams().width = logo_width;
        menu_logo.getLayoutParams().height = logo_height;

        solo.getLayoutParams().height = button_height;
        solo.getLayoutParams().width = button_width;
        versus.getLayoutParams().height = button_height;
        versus.getLayoutParams().width = button_width;
        tof.getLayoutParams().height = button_height;
        tof.getLayoutParams().width = button_width;
        identification.getLayoutParams().height = button_height;
        identification.getLayoutParams().width = button_width;

        int buttonIconHeight = (int) (button_height * 0.25);
        image1.getLayoutParams().height = buttonIconHeight;
        image2.getLayoutParams().height = buttonIconHeight;
        image3.getLayoutParams().height = buttonIconHeight;
        image4.getLayoutParams().height = buttonIconHeight;

        solo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent soloIntent = new Intent(MainMenu.this, GameSolo.class);
                MainMenu.this.startActivity(soloIntent);
                finish();
            }
        });

        versus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent tofIntent = new Intent(MainMenu.this, GameVersus.class);
                MainMenu.this.startActivity(tofIntent);
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

        identification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent tofIntent = new Intent(MainMenu.this, GameFindTheNumber.class);
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