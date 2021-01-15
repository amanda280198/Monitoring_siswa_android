package com.example.testingskripsinew;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class LoginAsdos extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_asdos);
    }

    public void btnLoginAsdos(View view) {
        Intent i = new Intent(this, MainActivityAsdos.class);
        this.startActivity(i);
    }

    public void btnRegister(View view) {
        Intent iAsdos = new Intent(this, Register.class);
        this.startActivity(iAsdos);
    }
}