package com.example.testingskripsinew;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.testingskripsinew.user.MainActivityUser;

public class MainActivityAsdos extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_asdos);
    }

    public void btnHistoryMhs(View view) {
        Intent i = new Intent(this, MainActivityUser.class);
        this.startActivity(i);
    }

    public void btnProfilAsdos(View view) {
        Intent i = new Intent(this, ProfilAsdos.class);
        this.startActivity(i);
    }
}