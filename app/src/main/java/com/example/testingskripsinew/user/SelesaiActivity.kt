package com.example.testingskripsinew.user

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.testingskripsinew.databinding.ActivitySelesaiBinding

class SelesaiActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySelesaiBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelesaiBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun btnDone(view: View) {
        val i = Intent(this, LoginUser::class.java)
        startActivity(i)
        finishAffinity()
    }

}