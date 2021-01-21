package com.example.testingskripsinew.user

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.testingskripsinew.R
import com.example.testingskripsinew.databinding.ActivityLoginUserBinding
import com.example.testingskripsinew.model.DataUser
import com.example.testingskripsinew.utils.Data
import com.google.firebase.database.*

class LoginUser : AppCompatActivity() {
    private lateinit var binding: ActivityLoginUserBinding
    private lateinit var database: FirebaseDatabase
    private lateinit var myRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //ini wajib di kenalin di oncreate
        database = FirebaseDatabase.getInstance()
        // ini getReference buat alamat path nya
        myRef = database.getReference(Data.USER_DATA)

        binding.btnLoginUser.setOnClickListener {
            val inUser = binding.usernameUser.text.toString().trim { it <= ' ' }
            val inPassword = binding.passUser.text.toString().trim { it <= ' ' }

            var inputKosong = false
            when {
                inUser.isEmpty() -> {
                    inputKosong = true
                    binding.usernameUser.error = getString(R.string.empty_alert)
                }
                inPassword.isEmpty() -> {
                    inputKosong = true
                    binding.passUser.error = getString(R.string.empty_alert)
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
                val value = snapshot.getValue(DataUser::class.java)
                if (value?.npm == userName && value.pass == passUser) {
                    val intent = Intent(this@LoginUser, MainActivityUser::class.java)
                    intent.putExtra(MainActivityUser.EXTRA_USER, value.nama)
                    intent.putExtra(MainActivityUser.EXTRA_NPM, value.npm)
                    intent.putExtra(MainActivityUser.EXTRA_STATUS, value.status)
                    startActivity(intent)
                } else {
                    Toast.makeText(this@LoginUser, "Maaf Data Tidak Tersedia", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@LoginUser, "Maaf Data Tidak Tersedia", Toast.LENGTH_SHORT)
                    .show()
            }

        })
    }
}