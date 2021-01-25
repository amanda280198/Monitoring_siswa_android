package com.example.testingskripsinew.user

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.testingskripsinew.R
import com.example.testingskripsinew.databinding.ActivityKelasBinding
import com.example.testingskripsinew.model.DataKelas
import com.example.testingskripsinew.utils.Data
import com.google.firebase.database.*

class KelasActivity : AppCompatActivity() {
    private lateinit var binding: ActivityKelasBinding
    private lateinit var database: FirebaseDatabase
    private lateinit var myRef: DatabaseReference
    private lateinit var myRef1: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKelasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = FirebaseDatabase.getInstance()
        myRef = database.getReference(Data.KELAS_DATA)
        myRef1 = database.getReference(Data.KELAS_STATUS)

        getDataKelas()
    }

    private fun getDataKelas() {
        myRef.child(Data.qrKode).child(Data.npmUser.toString()).addValueEventListener(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val value = snapshot.getValue(DataKelas::class.java)
                value?.let { onShowData(it) }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@KelasActivity, "Maaf Data Tidak Tersedia", Toast.LENGTH_SHORT)
                    .show()
            }

        })

        myRef1.child(Data.qrKode).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val value = snapshot.getValue(String::class.java)
                onDoneClass(value)
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    private fun onDoneClass(value: String?) {
        with(binding) {
            if (value.toString() == "1") {
                btnCheckout.isClickable = true
                btnCheckout.backgroundTintList =
                    ContextCompat.getColorStateList(this@KelasActivity, R.color.torques)
                btnCheckout.setOnClickListener {
                    myRef.child(Data.qrKode).child(Data.npmUser.toString()).child("statuskeluar")
                        .setValue("1")
                }
            } else {
                btnCheckout.isClickable = false
                btnCheckout.backgroundTintList =
                    ContextCompat.getColorStateList(this@KelasActivity, R.color.colorLight)
            }
        }
    }

    private fun onShowData(dataKelas: DataKelas) {
        with(binding) {
            namaUser.text = dataKelas.nama
            npm.text = dataKelas.npm

            if (dataKelas.izin == "0") {
                btnIzin.isClickable = true
                btnIzin.backgroundTintList =
                    ContextCompat.getColorStateList(this@KelasActivity, R.color.torques)
                btnIzin.setOnClickListener {
                    myRef.child(Data.qrKode).child(Data.npmUser.toString()).child("izin")
                        .setValue("1")
                }
            } else {
                btnIzin.isClickable = false
                btnIzin.backgroundTintList =
                    ContextCompat.getColorStateList(this@KelasActivity, R.color.colorLight)
            }
        }
    }
}