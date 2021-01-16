package com.example.testingskripsinew;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivityUser extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_user);
    }

    public void btnPresensiPribadi(View view) {
        Intent i = new Intent(this, ViewMapsActivity.class);
        this.startActivity(i);
    }

    public void btnHistoryPribadi(View view) {
        Intent i = new Intent(this, HistoryPribadi_Hadir.class);
        this.startActivity(i);
    }

    public void btnProfilPribadi(View view) {
        Intent i = new Intent(this, ProfilUser.class);
        this.startActivity(i);
    }
}