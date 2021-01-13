package com.example.testingskripsinew.user

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Geocoder
import android.location.LocationManager
import android.os.Build
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
import kotlinx.android.synthetic.main.activity_jarak.*
import java.util.*
import kotlin.math.*

class JarakActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var binding: ActivityJarakBinding

    private lateinit var mMap: GoogleMap
    private lateinit var geofencingClient: GeofencingClient
    private lateinit var geofenceHelper: GeofenceHelper

    // lokasi statis berdasarkan Latitude dan Longitude -6.597723, 106.799559
    private val baseLatitude = -6.569395464817481
    private val baseLongitude = 106.77884033828049

    companion object {
        const val ID_LOCATION_PERMISSION = 0

        private const val FINE_LOCATION_ACCESS_REQUEST_CODE = 10001
        private const val BACKGROUND_LOCATION_ACCESS_REQUEST_CODE = 10002
        private const val GEOFENCE_RADIUS = 30.0
        private const val GEOFENCE_ID = "SOME_GEOFENCE_ID"
        const val TAG = "MapsActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJarakBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initMap()
        checkPermissionLocation()
        onClick()
    }

    private fun onClick() {
        binding.fabCheckIn.setOnClickListener {
            startScanLocation()
            Handler(Looper.getMainLooper()).postDelayed({
                getLocationCoordinat()
            }, 4000)
        }
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
                        status_jarak.text = jarak
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
//            binding.tvDaerah.text = address
            binding.statusLong.text = lon.toString()
            binding.statusLat.text = lat.toString()

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
        val rumahAing = LatLng(-6.56942189233, 106.778845096)
//        mMap.addMarker(MarkerOptions().position(rumahAing).title("Marker in Rumah Aing"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(rumahAing, 16F))

        enableUserLocation()

        val zone1 = LatLng(-6.567361229824218, 106.77843511104584)
        val zone2 = LatLng(-6.565359105532554, 106.78024895489216)
        val zone3 = LatLng(-6.570681987174704, 106.7810794338584)

        testZona(rumahAing)
//        testZona(zone2)
//        testZona(zone3)

        mMap.setOnMapLongClickListener { latLng ->
            if (Build.VERSION.SDK_INT >= 29) {
                if (ContextCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_BACKGROUND_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    handleMapLongClick(latLng)
                } else {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(
                            this,
                            Manifest.permission.ACCESS_BACKGROUND_LOCATION
                        )
                    ) {
                        ActivityCompat.requestPermissions(
                            this,
                            arrayOf(Manifest.permission.ACCESS_BACKGROUND_LOCATION),
                            BACKGROUND_LOCATION_ACCESS_REQUEST_CODE
                        )
                    } else {
                        ActivityCompat.requestPermissions(
                            this,
                            arrayOf(Manifest.permission.ACCESS_BACKGROUND_LOCATION),
                            BACKGROUND_LOCATION_ACCESS_REQUEST_CODE
                        )
                    }
                }
            } else {
                handleMapLongClick(latLng)
            }
        }
    }

    private fun testZona(latLng: LatLng) {
//        mMap.addMarker(MarkerOptions().position(latLng))
        addMarker(latLng)
        addCircle(latLng, GEOFENCE_RADIUS)
    }

    private fun handleMapLongClick(latLng: LatLng?) {
        Log.d(TAG, "LatLng = $latLng")
        if (latLng != null) {
            mMap.clear()
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

    private fun addGeofence(latLng: LatLng, radius: Float) {
        val geofence = geofenceHelper.getGeofence(
            GEOFENCE_ID, latLng, radius,
            Geofence.GEOFENCE_TRANSITION_ENTER or Geofence.GEOFENCE_TRANSITION_DWELL or Geofence.GEOFENCE_TRANSITION_EXIT
        )
        val geofencingRequest = geofenceHelper.getGeofencingRequest(geofence)
//        val pendingIntent = geofenceHelper.getPendingInten()
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

    private fun addCircle(latLng: LatLng, radius: Double) {
        val circleOptions = CircleOptions()
        circleOptions.center(latLng)
        circleOptions.radius(radius)
        circleOptions.strokeColor(Color.argb(255, 0, 255, 0))
        circleOptions.fillColor(Color.argb(64, 0, 255, 0))
        circleOptions.strokeWidth(4F)
        mMap.addCircle(circleOptions)
    }
}