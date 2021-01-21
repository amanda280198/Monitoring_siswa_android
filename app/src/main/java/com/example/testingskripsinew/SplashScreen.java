package com.example.testingskripsinew;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.testingskripsinew.jadwal.ScheduleActivity;
import com.example.testingskripsinew.user.JarakActivity;

public class SplashScreen extends AppCompatActivity {
    private static final int SPLASH_TIME_OUT = 3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(() -> {

            Intent i = new Intent(SplashScreen.this, PilihAkunActivity.class);
//            Intent i = new Intent(SplashScreen.this, JarakActivity.class);
            SplashScreen.this.startActivity(i);

            SplashScreen.this.finish();
        }, SPLASH_TIME_OUT);

    }
}