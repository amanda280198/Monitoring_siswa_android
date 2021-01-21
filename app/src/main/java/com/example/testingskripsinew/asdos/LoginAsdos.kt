package com.example.testingskripsinew.asdos

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.testingskripsinew.R
import com.example.testingskripsinew.databinding.ActivityLoginAsdosBinding
import com.example.testingskripsinew.model.DataAsdos
import com.example.testingskripsinew.utils.Data
import com.google.firebase.database.*

class LoginAsdos : AppCompatActivity() {
    private lateinit var binding: ActivityLoginAsdosBinding
    private lateinit var database: FirebaseDatabase
    private lateinit var myRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginAsdosBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //ini wajib di kenalin di oncreate
        database = FirebaseDatabase.getInstance()
        // ini getReference buat alamat path nya
        myRef = database.getReference(Data.ASDOS_DATA)

        binding.btnLoginAsdos.setOnClickListener {
            val inUser = binding.usernameAsdos.text.toString().trim { it <= ' ' }
            val inPassword = binding.passAsdos.text.toString().trim { it <= ' ' }

            var inputKosong = false
            when {
                inUser.isEmpty() -> {
                    inputKosong = true
                    binding.usernameAsdos.error = getString(R.string.empty_alert)
                }
                inPassword.isEmpty() -> {
                    inputKosong = true
                    binding.passAsdos.error = getString(R.string.empty_alert)
                }
            }
            if (!inputKosong) {
                getDataUser(inUser, inPassword)
            }
        }
    }

    private fun getDataUser(userName: String, passUser: String) {
        myRef.child(userName).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val value = snapshot.getValue(DataAsdos::class.java)
                if (value?.npm == userName && value.pass == passUser) {
                    val intent = Intent(this@LoginAsdos, MainActivityAsdos::class.java)
                    intent.putExtra(MainActivityAsdos.EXTRA_USER, value.nama)
                    intent.putExtra(MainActivityAsdos.EXTRA_NPM, value.npm)
                    intent.putExtra(MainActivityAsdos.EXTRA_STATUS, value.status)
                    startActivity(intent)
                } else {
                    Toast.makeText(this@LoginAsdos, "Maaf Data Tidak Tersedia", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@LoginAsdos, "Maaf Data Tidak Tersedia", Toast.LENGTH_SHORT)
                    .show()
            }

        })
    }
}