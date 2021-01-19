package com.example.testingskripsinew.helper

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class GeofenceBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.
        Toast.makeText(context, "Masuk Ke zona Bro", Toast.LENGTH_SHORT).show()
    }
}
