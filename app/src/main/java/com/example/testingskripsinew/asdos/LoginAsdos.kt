package com.example.testingskripsinew.asdos

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.testingskripsinew.MainActivityAsdos
import com.example.testingskripsinew.R
import com.example.testingskripsinew.databinding.ActivityLoginAsdosBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class LoginAsdos : AppCompatActivity() {
    private lateinit var binding: ActivityLoginAsdosBinding
    private lateinit var database: FirebaseDatabase
    private lateinit var myRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_asdos)
    }

    fun btnLoginAsdos(view: View?) {
        val i = Intent(this, MainActivityAsdos::class.java)
        this.startActivity(i)
    }
}