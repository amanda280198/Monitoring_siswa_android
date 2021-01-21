package com.example.testingskripsinew;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.testingskripsinew.model.UserModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginUser extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference myRef;
    EditText etUsername, etPassword;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user);

        etUsername = findViewById(R.id.xusername_user);
        etPassword = findViewById(R.id.xpass_user);

        btnLogin = findViewById(R.id.btn_login_user);

//        init Firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = etUsername.getText().toString();
                final String password = etPassword.getText().toString();
                if (TextUtils.isEmpty(username)) {
                    etUsername.setError("Harus diisi!");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    etPassword.setError("Harus diisi!");
                    return;
                }

                final ProgressDialog mDialog = new ProgressDialog(LoginUser.this);
                mDialog.setMessage("Autentikasi data...");
                mDialog.show();

                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        //Check data user
                        if (snapshot.child(etUsername.getText().toString()).exists()) {

                            //Get info data usernya
                            mDialog.dismiss();
                            UserModel userModel = snapshot.child(etUsername.getText().toString()).getValue(UserModel.class);
                            if (userModel.getPassword().equals(etPassword.getText().toString())) {
                                Intent intentLogin = new Intent(LoginUser.this, MainActivityUser.class);
                                startActivity(intentLogin);
                                Toast.makeText(LoginUser.this, "Login berhasil!", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(LoginUser.this, "Login gagal!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            mDialog.dismiss();
                            Toast.makeText(LoginUser.this, "Data user tidak tersedia!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }

    public void btnRegister(View view) {
        Intent iUser = new Intent(this, Register.class);
        this.startActivity(iUser);
    }
}

//Coding teh manda

//        //ini wajib di kenalin di oncreate
//        database = FirebaseDatabase.getInstance();
//        // ini getReference buat alamat path nya
//        myRef = database.getReference("dataAsdos");
//
//        btn_login_user.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                String username = xusername_user.getText().toString();
//                final String pass_user = xpass_user.getText().toString();
//
//                myRef = FirebaseDatabase.getInstance().getReference().child("dataUser")
//                        .child("dataUser").child(username);
//
//                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        if (snapshot.exists()) {
//                            Toast.makeText(getApplicationContext(), "Username ada :)",
//                                    Toast.LENGTH_SHORT).show();
//                            //pindah activity
//                            Intent gotothome = new Intent(LoginUser.this,
//                                    MainActivityUser.class);
//                            startActivity(gotothome);
//                        } else {
//                            Toast.makeText(getApplicationContext(), "Username tidak ada!",
//                                    Toast.LENGTH_SHORT).show();
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//                        Toast.makeText(getApplicationContext(), "Database error!", Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
//        });

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
