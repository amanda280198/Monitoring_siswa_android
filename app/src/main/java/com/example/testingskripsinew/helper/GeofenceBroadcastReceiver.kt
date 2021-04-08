package com.example.testingskripsinew.helper

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.example.testingskripsinew.model.DataStatus
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofenceStatusCodes
import com.google.android.gms.location.GeofencingEvent
import org.greenrobot.eventbus.EventBus

class GeofenceBroadcastReceiver : BroadcastReceiver() {

    private val TAG = "GeofenceBroadcastReceiv"

    override fun onReceive(context: Context, intent: Intent) {
        val geofencingEvent = GeofencingEvent.fromIntent(intent)

        if (geofencingEvent.hasError()) {
            val errorMessage = GeofenceStatusCodes
                .getStatusCodeString(geofencingEvent.errorCode)
            Log.d(TAG, errorMessage)
            return
        }

        val geofenceList = geofencingEvent.triggeringGeofences

        for (geofence in geofenceList) {
            Log.d(TAG, "onReceive: " + geofence.requestId)
        }

        when (geofencingEvent.geofenceTransition) {
            Geofence.GEOFENCE_TRANSITION_ENTER -> {
                Toast.makeText(context, "Kamu berada didalam zona", Toast.LENGTH_SHORT).show()
                val status = DataStatus()
                status.geoStatus = "1"
                EventBus.getDefault().post(status)
            }
            Geofence.GEOFENCE_TRANSITION_DWELL -> {
                Toast.makeText(context, "Kamu berada didalam zona", Toast.LENGTH_SHORT).show()
                val status = DataStatus()
                status.geoStatus = "2"
                EventBus.getDefault().post(status)
            }
            Geofence.GEOFENCE_TRANSITION_EXIT -> {
                val status = DataStatus()
                status.geoStatus = "3"
                EventBus.getDefault().post(status)
            }
        }
    }
}
