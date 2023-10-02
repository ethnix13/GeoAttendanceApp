package com.example.geoattendanceapp

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.location.LocationServices

class GeofenceHelper(private val context: Context) {
    @SuppressLint("MissingPermission")
    fun createGeofence(geofenceId: String, latitude: Double, longitude: Double, radius: Float) {
        val geofencingRequest = createGeofenceRequest(geofenceId, latitude, longitude, radius)
        val geofencePendingIntent = createGeofencePendingIntent()

        LocationServices.getGeofencingClient(context)
            .addGeofences(geofencingRequest, geofencePendingIntent)
            .addOnSuccessListener {
                Log.d("L","Geofence addition successful!!")
            }
            .addOnFailureListener { e ->
                Log.d("L","Geofence addition failed: " + e.message)
            }
    }

    private fun createGeofenceRequest(geofenceId: String, latitude: Double, longitude: Double, radius: Float): GeofencingRequest {
        val geofence = Geofence.Builder()
            .setRequestId(geofenceId)
            .setCircularRegion(latitude, longitude, radius)
            .setExpirationDuration(Geofence.NEVER_EXPIRE)
            .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER)
            .build()

        val geofenceList = mutableListOf(geofence)

        return GeofencingRequest.Builder()
            .addGeofences(geofenceList)
            .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
            .build()
    }

    private fun createGeofencePendingIntent(): PendingIntent {
        val intent = Intent(context, GeofenceBroadcastReceiver::class.java)
        return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)
    }
}