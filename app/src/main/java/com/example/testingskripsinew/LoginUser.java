package com.example.testingskripsinew;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginUser extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user);
        //ini wajib di kenalin di oncreat
        database = FirebaseDatabase.getInstance();
        // ini getReference buat alamat path nya
        myRef = database.getReference("dataAsdos");
    }

    public void btnLogin(View view) {
        Intent i = new Intent(this, MainActivityUser.class);
        this.startActivity(i);

//        // Read from the database
//        myRef.child("065116120").child("nama")
//                .addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        // This method is called once with the initial value and again
//                        // whenever data at this location is updated.
//                        String value = dataSnapshot.getValue(String.class);
//                        Log.d("onLoginUser", "Value is: " + value);
//                        Toast.makeText(LoginUser.this, value, Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError error) {
//                        // Failed to read value
//                        Log.w("onLoginUser", "Failed to read value.", error.toException());
//                    }
//                });
    }
}