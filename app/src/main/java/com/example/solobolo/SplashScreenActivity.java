package com.example.solobolo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

public class SplashScreenActivity extends AppCompatActivity {

    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        TextView appNameView = (TextView) findViewById(R.id.splash_screen_app_name);
        Shader textShaderOne = new LinearGradient(0f, 0f, 0f, appNameView.getTextSize(),
                getResources().getColor(R.color.logo_color_200), getResources().getColor(R.color.logo_color_100), Shader.TileMode.CLAMP);
        appNameView.getPaint().setShader(textShaderOne);

        TextView appDescView = (TextView) findViewById(R.id.splash_screen_app_description);
        Shader textShaderTwo = new LinearGradient(0f, 0f, 0f, appDescView.getTextSize(),
                getResources().getColor(R.color.logo_color_100), getResources().getColor(R.color.logo_color_200), Shader.TileMode.CLAMP);
        appDescView.getPaint().setShader(textShaderTwo);


        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreenActivity.this, LoginRegisterActivity.class);
                startActivity(intent);
                finish();
            }
        }, 3000);

    }
}