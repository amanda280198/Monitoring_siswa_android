package com.example.testingskripsinew.asdos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.testingskripsinew.databinding.ActivityMonitoringKelasBinding

class MonitoringKelasActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMonitoringKelasBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMonitoringKelasBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}