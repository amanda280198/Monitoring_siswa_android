package com.example.testingskripsinew.asdos

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.testingskripsinew.PilihAkunActivity
import com.example.testingskripsinew.R
import com.example.testingskripsinew.databinding.ActivityProfilAsdosBinding
import com.example.testingskripsinew.utils.Data

class ProfilAsdos : AppCompatActivity() {
    private lateinit var binding: ActivityProfilAsdosBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfilAsdosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.namaAsdos.text = Data.namaAsdos
        binding.npm.text = Data.npmAsdos
    }

    fun btnBack(view: View?) {
        finish()
    }

    fun btnLogout(view: View?) {
        val i = Intent(this, PilihAkunActivity::class.java)
        this.startActivity(i)
        finishAffinity()
    }
}