package com.example.testingskripsinew

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.testingskripsinew.asdos.LoginAsdos
import com.example.testingskripsinew.user.LoginUser

class PilihAkunActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pilih_akun)
    }

    fun btnUser(view: View?) {
        val i = Intent(this, LoginUser::class.java)
        this.startActivity(i)
    }

    fun btnAsdos(view: View?) {
        val i = Intent(this, LoginAsdos::class.java)
        this.startActivity(i)
    }
}