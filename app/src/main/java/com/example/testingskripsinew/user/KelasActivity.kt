package com.example.testingskripsinew.user

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.testingskripsinew.databinding.ActivityKelasBinding

class KelasActivity : AppCompatActivity() {
    private lateinit var binding: ActivityKelasBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKelasBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}