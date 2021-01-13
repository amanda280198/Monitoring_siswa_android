package com.example.testingskripsinew;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginUser extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference myRef;
    EditText xusername_user, xpass_user;
    Button btn_login_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user);

        xusername_user = findViewById(R.id.xusername_user);
        xpass_user = findViewById(R.id.xpass_user);
        btn_login_user = findViewById(R.id.btn_login_user);


        //ini wajib di kenalin di oncreate
        database = FirebaseDatabase.getInstance();
        // ini getReference buat alamat path nya
        myRef = database.getReference("dataAsdos");

        btn_login_user.setOnClickListener(view -> {

            String username = xusername_user.getText().toString();
            String pass_user = xpass_user.getText().toString();

            myRef = FirebaseDatabase.getInstance().getReference().child("dataUser")
                    .child(username);

            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        Toast.makeText(getApplicationContext(), "Username ada :)", Toast.LENGTH_SHORT).show();
                        String value = snapshot.getValue(String.class);
                        Toast.makeText(LoginUser.this, value, Toast.LENGTH_SHORT).show();
                        //pindah activity
//                        Intent gotothome = new Intent(LoginUser.this, MainActivityUser.class);
//                        startActivity(gotothome);
                    } else {
                        Toast.makeText(getApplicationContext(), "Username tidak ada!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(getApplicationContext(), "Database error!", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}
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
