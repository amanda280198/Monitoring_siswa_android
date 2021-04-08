package com.example.testingskripsinew.user

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.testingskripsinew.jadwal.ScheduleActivity
//import com.example.testingskripsinew.ViewMapsActivity
import com.example.testingskripsinew.databinding.ActivityMainUserBinding
import com.example.testingskripsinew.model.DataUser
import com.example.testingskripsinew.utils.Data

class MainActivityUser : AppCompatActivity() {
    private lateinit var binding: ActivityMainUserBinding
    private lateinit var inUser: String
    private lateinit var inNpm: String
    private lateinit var status: String

    private val cameraPermissionRequestCode = 1

    companion object {
        const val EXTRA_DATA_USER = "data_user"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val item = intent.getParcelableExtra<DataUser>(EXTRA_DATA_USER)
        inUser = item?.nama.toString()
        inNpm = item?.npm.toString()
        status = item?.status.toString()

        binding.namaUser.text = inUser
    }

    fun btnPresensiPribadi(view: View?) {
        startScanning()
    }

    fun btnHistoryPribadi(view: View?) {

    }

    fun btnProfilPribadi(view: View?) {
        val i = Intent(this, ProfilUser::class.java)
        this.startActivity(i)
        Data.namaUser = inUser
        Data.npmUser = inNpm
    }

    fun btnJadwalUser(view: View) {
        val i = Intent(this, ScheduleActivity::class.java)
        this.startActivity(i)
        Data.status = status
        Data.npmUser = inNpm
    }

    private fun startScanning() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            val i = Intent(this, ReaderScanActivity::class.java)
            this.startActivity(i)
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                cameraPermissionRequestCode
            )
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == cameraPermissionRequestCode) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                val i = Intent(this, ReaderScanActivity::class.java)
                this.startActivity(i)
            }
        }
    }
}