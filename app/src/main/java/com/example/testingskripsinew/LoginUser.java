package com.example.testingskripsinew;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class LoginUser extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user);
    }

    public void btnLogin(View view) {
        Intent i = new Intent(this, MainActivityUser.class);
        this.startActivity(i);
    }
}