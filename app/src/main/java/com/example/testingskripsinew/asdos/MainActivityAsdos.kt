package com.example.testingskripsinew.asdos

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.testingskripsinew.databinding.ActivityMainAsdosBinding
import com.example.testingskripsinew.user.MainActivityUser
import com.example.testingskripsinew.utils.Data

class MainActivityAsdos : AppCompatActivity() {
    private lateinit var binding: ActivityMainAsdosBinding
    private lateinit var inUser: String
    private lateinit var inNpm: String

    companion object {
        const val EXTRA_USER = "data_user"
        const val EXTRA_NPM = "data_npm"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainAsdosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        inUser = intent.getStringExtra(EXTRA_USER).toString()
        inNpm = intent.getStringExtra(EXTRA_NPM).toString()

        binding.namaAsdos.text = inUser
    }

    fun btnHistoryMhs(view: View?) {
        val i = Intent(this, MainActivityUser::class.java)
        this.startActivity(i)
    }

    fun btnProfilAsdos(view: View?) {
        val i = Intent(this, ProfilAsdos::class.java)
        this.startActivity(i)
        Data.namaAsdos = inUser
        Data.npmAsdos = inNpm
    }

    fun btnJadwalAsdos(view: View) {}
}