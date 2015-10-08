package com.akiniyalocts.system76.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.akiniyalocts.system76.R;
import com.akiniyalocts.system76.TypefaceHelper;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity {

    private final static int SPLASH_TIME_OUT = 3100;

    @Bind(R.id.imageView)
    ImageView logo;

    @Bind(R.id.system)
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_splash);
        ButterKnife.bind(this);
        hideSystemUI();

        text.setTypeface(TypefaceHelper.get(this, "UbuntuMono-Italic"));


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                startActivity(new Intent(SplashActivity.this, MainActivity.class));

                finish();
            }
        }, SPLASH_TIME_OUT);

        animate();
    }

    private void animate(){
        YoYo.with(Techniques.FadeIn)
                .duration(3000)
             .playOn(text);

        YoYo.with(Techniques.RollIn)
                .duration(2000)
                .playOn(logo);

    }

    private void hideSystemUI() {

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);
    }



}
