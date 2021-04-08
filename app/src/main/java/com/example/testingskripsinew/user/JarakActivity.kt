package com.example.testingskripsinew.user

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Geocoder
import android.location.LocationManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.testingskripsinew.R
import com.example.testingskripsinew.databinding.ActivityJarakBinding
import com.example.testingskripsinew.helper.GeofenceHelper
import com.example.testingskripsinew.model.DataKelas
import com.example.testingskripsinew.model.DataStatus
import com.example.testingskripsinew.utils.Data
import com.firebase.geofire.GeoFire
import com.firebase.geofire.GeoLocation
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.FirebaseError
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.*


class JarakActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var binding: ActivityJarakBinding
    private lateinit var database: FirebaseDatabase
    private lateinit var myRef: DatabaseReference

    private lateinit var mMap: GoogleMap
    private lateinit var geofencingClient: GeofencingClient
    private lateinit var geofenceHelper: GeofenceHelper
    private lateinit var rumahAing: LatLng

    // lokasi statis berdasarkan Latitude dan Longitude -6.597723, 106.799559
//    private val baseLatitude = -6.569395464817481
//    private val baseLongitude = 106.77884033828049
    private val baseLatitude = -6.595676766467622
    private val baseLongitude = 106.76432823679038

    companion object {
        const val ID_LOCATION_PERMISSION = 0

        private const val FINE_LOCATION_ACCESS_REQUEST_CODE = 10001
        private const val GEOFENCE_RADIUS = 30.0
        private const val GEOFENCE_ID = "SOME_GEOFENCE_ID"
        const val TAG = "MapsActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJarakBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = FirebaseDatabase.getInstance()
        myRef = database.getReference(Data.KELAS_DATA)

        EventBus.getDefault().register(this)

        initMap()
        checkPermissionLocation()
        onShowTime()

//        binding.fabCheckIn.setOnClickListener {
//            startScanLocation()
//            Handler(Looper.getMainLooper()).postDelayed({
//                getLocationCoordinat()
//            }, 4000)
//        }

    }

    private fun startScanLocation() {
        with(binding) {
            rippleBackground.startRippleAnimation()
            group.visibility = View.GONE
        }
    }

    private fun stopScanLocation() {
        with(binding) {
            rippleBackground.stopRippleAnimation()
            binding.fabCheckIn.visibility = View.GONE
        }
    }

    fun btnCheckin(view: View) {
        onPushData(
            DataKelas(
                "0",
                "0",
                Data.jam,
                Data.jarak,
                Data.qrKode,
                "${Data.lat}, ${Data.lon}",
                Data.idNama,
                Data.npmUser,
                "1",
                "0",
                "0"
            )
        )
    }

    private fun onPushData(dataKelas: DataKelas) {
        myRef.child(Data.qrKode).child("pertemuan${Data.temuKode}").child(Data.npmUser.toString())
//        myRef.child(Data.qrKode).child(Data.npmUser.toString())
            .setValue(dataKelas)
            .addOnSuccessListener {
                val i = Intent(this, KelasActivity::class.java)
                startActivity(i)
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

                    getAddressesLatLot(currentLat, currentLong)

                    with(binding) {
                        group.visibility = View.VISIBLE
                        binding.statusJarak.text = jarak
                        Data.jarak = jarak
                    }

                    stopScanLocation()
                }
            } else {
                showTurnOnLocation()
//                mMap.isMyLocationEnabled = true
            }
        } else {
            reqPermission()
        }
    }

    private fun getAddressesLatLot(lat: Double, lon: Double) {
        try {
            val geocoder = Geocoder(this, Locale.getDefault())
            val addresses = geocoder.getFromLocation(lat, lon, 1)
            val address = addresses[0].getAddressLine(0)
            Log.d("alamat", "lokasi $address")
            binding.statusLong.text = lon.toString()
            binding.statusLat.text = lat.toString()
            binding.alerText.text = getString(R.string.alert_txt_succes)
            binding.txtTitle.text = getString(R.string.alert_txt_title_succes)

            Data.lon = lon.toString()
            Data.lat = lat.toString()
        } catch (e: Exception) {
            e.printStackTrace()
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
        if (reqCode == ID_LOCATION_PERMISSION) {
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
            ), ID_LOCATION_PERMISSION
        )
    }

    private fun showTurnOnLocation() {
        Toast.makeText(this, "Silahakan Aktifkan Lokasi Kamu", Toast.LENGTH_SHORT).show()
        startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
    }

    fun btnBack(view: View) {
        finish()
    }

    // enable load map google
    private fun initMap() {
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        geofencingClient = LocationServices.getGeofencingClient(this)
        geofenceHelper = GeofenceHelper(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        rumahAing = LatLng(baseLatitude, baseLongitude)

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(rumahAing, 18f))
        enableUserLocation()

        testZona(rumahAing)
    }

    private fun testZona(latLng: LatLng?) {
        if (latLng != null) {
//            mMap.clear()
            addMarker(latLng)
            addCircle(latLng, GEOFENCE_RADIUS)
            addGeofence(latLng, GEOFENCE_RADIUS.toFloat())
        }
    }

    private fun enableUserLocation() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            mMap.isMyLocationEnabled = true
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    FINE_LOCATION_ACCESS_REQUEST_CODE
                )
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    FINE_LOCATION_ACCESS_REQUEST_CODE
                )
            }
        }
    }

    @Suppress("SameParameterValue")
    private fun addGeofence(latLng: LatLng, radius: Float) {
        val geofence = geofenceHelper.getGeofence(
            GEOFENCE_ID, latLng, radius,
            Geofence.GEOFENCE_TRANSITION_ENTER or Geofence.GEOFENCE_TRANSITION_DWELL or Geofence.GEOFENCE_TRANSITION_EXIT
        )
        val geofencingRequest = geofenceHelper.getGeofencingRequest(geofence)
        val pendingIntent = geofenceHelper.geofencePendingIntent

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        geofencingClient.addGeofences(geofencingRequest, pendingIntent)?.run {
            addOnSuccessListener {
                Log.d(TAG, "Berhasil Gan")
            }
            addOnFailureListener {
                val errorMessage = geofenceHelper.getErrorString(it)
                Log.d(TAG, "Gagal Gan: $errorMessage")
            }
        }
    }

    private fun addMarker(latLng: LatLng) {
        val markerOptions: MarkerOptions = MarkerOptions().position(latLng)
        mMap.addMarker(markerOptions)
    }

    @Suppress("SameParameterValue")
    private fun addCircle(latLng: LatLng, radius: Double) {
        val circleOptions = CircleOptions()
        circleOptions.center(latLng)
        circleOptions.radius(radius)
        circleOptions.strokeColor(Color.argb(255, 0, 255, 0))
        circleOptions.fillColor(Color.argb(64, 0, 255, 0))
        circleOptions.strokeWidth(4F)
        mMap.addCircle(circleOptions)
    }


    @Subscribe
    fun onMessageEvent(data: DataStatus?) {
        binding.fabCheckIn.setOnClickListener {
            startScanLocation()
            Handler(Looper.getMainLooper()).postDelayed({
                if (data != null) {
                    if (data.geoStatus == "1" || data.geoStatus == "2") {
                        getLocationCoordinat()
                        Toast.makeText(this, "Kamu berada didalam zona", Toast.LENGTH_SHORT).show()
                    } else if (data.geoStatus == "3") {
                        Toast.makeText(this, "Kamu keluar zona", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Kamu berada diluar zona", Toast.LENGTH_SHORT).show()
                }
            }, 4000)
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

}