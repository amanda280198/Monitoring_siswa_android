package com.example.testingskripsinew;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class PilihAkunActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pilih_akun);
    }

    public void btnUser(View view) {
        Intent i = new Intent(this, LoginUser.class);
        this.startActivity(i);
    }

    public void btnAsdos(View view) {
        Intent i = new Intent(this, LoginAsdos.class);
        this.startActivity(i);
    }
}