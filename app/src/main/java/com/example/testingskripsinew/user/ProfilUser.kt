package com.example.testingskripsinew.user

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.testingskripsinew.PilihAkunActivity
import com.example.testingskripsinew.databinding.ActivityProfilUserBinding
import com.example.testingskripsinew.utils.Data

class ProfilUser : AppCompatActivity() {
    private lateinit var binding: ActivityProfilUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfilUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.namaUser.text = Data.namaUser
        binding.npm.text = Data.npmUser
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