package com.example.testingskripsinew.user

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.testingskripsinew.R
import com.example.testingskripsinew.databinding.ActivityKelasBinding
import com.example.testingskripsinew.model.DataKelas
import com.example.testingskripsinew.utils.Data
import com.firebase.geofire.GeoFire
import com.firebase.geofire.GeoLocation
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.GoogleMap
import com.google.firebase.database.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.*

class KelasActivity : AppCompatActivity() {
    private lateinit var binding: ActivityKelasBinding
    private lateinit var database: FirebaseDatabase
    private lateinit var myRef: DatabaseReference
    private lateinit var myRef1: DatabaseReference

    private lateinit var mMap: GoogleMap
    private val baseLatitude = -6.595676766467622
    private val baseLongitude = 106.76432823679038
    private lateinit var mainHandler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKelasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = FirebaseDatabase.getInstance()
        myRef = database.getReference(Data.KELAS_DATA)
        myRef1 = database.getReference(Data.KELAS_STATUS)

        checkPermissionLocation()
        getDataKelas()
        onShowTime()

        mainHandler = Handler(Looper.getMainLooper())
    }

    private  val updateTextTask = object : Runnable {
        override fun run() {
            getLocationCoordinat()
            mainHandler.postDelayed(this, 3000)
        }
    }

    private fun onSetLocation(value: DataKelas?) {

        if (value?.izin == "1"){
            mainHandler.post(updateTextTask)
        }else{
            mainHandler.removeCallbacks(updateTextTask)
        }
    }

    private fun getDataKelas() {
        myRef.child(Data.qrKode).child("pertemuan${Data.temuKode}").child(Data.npmUser.toString())
//        myRef.child(Data.qrKode).child(Data.npmUser.toString())
            .addValueEventListener(object :
                ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val value = snapshot.getValue(DataKelas::class.java)
                    value?.let { onShowData(it) }
                    onSetLocation(value)
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(
                        this@KelasActivity, "Maaf Data Tidak Tersedia",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            })

        myRef1.child(Data.qrKode).child("pertemuan${Data.temuKode}")
            .addValueEventListener(object : ValueEventListener {
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
                    myRef.child(Data.qrKode).child("pertemuan${Data.temuKode}")
//                    myRef.child(Data.qrKode)
                        .child(Data.npmUser.toString()).child("statuskeluar")
                        .setValue("1")
                    myRef.child(Data.qrKode).child("pertemuan${Data.temuKode}")
//                    myRef.child(Data.qrKode)
                        .child(Data.npmUser.toString()).child("pertemuan")
                        .setValue(Data.temuKode)
                    myRef.child(Data.qrKode).child("pertemuan${Data.temuKode}")
//                    myRef.child(Data.qrKode)
                        .child(Data.npmUser.toString()).child("jamKeluar")
                        .setValue(Data.jam)
                        .addOnSuccessListener {
                            val i = Intent(this@KelasActivity, SelesaiActivity::class.java)
                            startActivity(i)
                        }
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
                    myRef.child(Data.qrKode).child("pertemuan${Data.temuKode}")
                        .child(Data.npmUser.toString()).child("izin")
                        .setValue("1")
                }
            } else {
                btnIzin.isClickable = false
                btnIzin.backgroundTintList =
                    ContextCompat.getColorStateList(this@KelasActivity, R.color.colorLight)
            }
        }
    }

    private fun onShowTime() {
        val thread = object : Thread() {
            override fun run() {
                try {
                    while (!isInterrupted) {
                        sleep(1000)
                        runOnUiThread {
                            val date = System.currentTimeMillis()
                            val sdf = SimpleDateFormat("dd MM yyyy", Locale.getDefault())
                            val jam = SimpleDateFormat("h:mm a", Locale.getDefault())
                            val dateString = sdf.format(date)
                            val timeString = jam.format(date)
                            Data.tanggal = dateString
                            Data.jam = timeString

                            val tglformat = Data.tanggal
                            tglformat.replace(' ', '_')
                        }
                    }
                } catch (e: InterruptedException) {
                }
            }
        }
        thread.start()
    }

    fun geoFireData(currentLat: Double, currentLong: Double) {
        val ref = FirebaseDatabase.getInstance().getReference("geofire")
        val geoFire = GeoFire(ref)
        geoFire.setLocation(Data.npmUser.toString(), GeoLocation(currentLat, currentLong))
        { _, error ->
            if (error != null) {
                Toast.makeText(this, "error", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "ok", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // harversine start
    private fun getLocationCoordinat() {
        if (checkPermission()) {
            if (isLoactionEnable()) {
                LocationServices.getFusedLocationProviderClient(this).lastLocation.addOnSuccessListener { location ->
                    val currentLat = location.latitude
                    val currentLong = location.longitude

                    val resultCoordinat = "$currentLat, $currentLong"

                    val distance = calculateDistance(
                        currentLat, currentLong,
                        baseLatitude, baseLongitude
                    ) * 1000

                    val jarak = "${distance.toInt()} meter"

                    Log.d("posisi titik", "lokasi $baseLatitude, $baseLongitude")
                    Log.d("posisi hp", "lokasi $resultCoordinat")

//                    geoFireData(currentLat, currentLong)
                    myRef.child(Data.qrKode).child("pertemuan${Data.temuKode}")
                        .child(Data.npmUser.toString()).child("koordinat")
                        .setValue(resultCoordinat)

                    myRef.child(Data.qrKode).child("pertemuan${Data.temuKode}")
                        .child(Data.npmUser.toString()).child("jarak")
                        .setValue(jarak)

                }
            } else {
                showTurnOnLocation()
            }
        } else {
            reqPermission()
        }
    }

    @Suppress("SameParameterValue")
    private fun calculateDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val r = 6372.8 // in kilometers

        val radiansLat1 = Math.toRadians(lat1)
        val radiansLat2 = Math.toRadians(lat2)
        val dLat = Math.toRadians(lat2 - lat1)
        val dLon = Math.toRadians(lon2 - lon1)
        return 2 * r * asin(
            sqrt(
                sin(dLat / 2).pow(2.0) + sin(dLon / 2).pow(2.0) * cos(radiansLat1) * cos(
                    radiansLat2
                )
            )
        )
    }

    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(
        reqCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(reqCode, permissions, grantResults)
        if (reqCode == JarakActivity.ID_LOCATION_PERMISSION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED ||
                grantResults[1] == PackageManager.PERMISSION_GRANTED
            ) {
                Toast.makeText(this, "Akses diizinkan", Toast.LENGTH_SHORT).show()
                if (!isLoactionEnable()) {
                    showTurnOnLocation()
                    mMap.isMyLocationEnabled = true
                }
            } else {
                Toast.makeText(this, "Akses Tidak diizinkan", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun checkPermissionLocation() {
        if (checkPermission()) {
            if (!isLoactionEnable()) {
                showTurnOnLocation()
//                mMap.isMyLocationEnabled = true
            }
        } else {
            reqPermission()
        }
    }

    private fun checkPermission(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }

    private fun isLoactionEnable(): Boolean {
        val locManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (locManager.isProviderEnabled(LocationManager.GPS_PROVIDER) && locManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
            )
        ) {
            return true
        }
        return false
    }

    private fun reqPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ), JarakActivity.ID_LOCATION_PERMISSION
        )
    }

    private fun showTurnOnLocation() {
        Toast.makeText(this, "Silahakan Aktifkan Lokasi Kamu", Toast.LENGTH_SHORT).show()
        startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
    }

}