package com.example.testingskripsinew;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class PilihAkunActivity extends AppCompatActivity {
//    FirebaseDatabase database;
//    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pilih_akun);
//        database = FirebaseDatabase.getInstance();
//        myRef = database.getReference("data");
//        myRef.child("test").setValue("Hello, Aku Mahasiswa");
    }

    public void btnUser(View view) {
        Intent i = new Intent(this, LoginUser.class);
        this.startActivity(i);
//        myRef.child("user").setValue("Hello, Aku Mahasiswa");
    }

    public void btnAsdos(View view) {
        Intent i = new Intent(this, LoginAsdos.class);
        this.startActivity(i);
//        myRef.child("asdos").setValue("Hello, Aku Asdos");
    }
}