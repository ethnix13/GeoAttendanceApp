package com.example.geoattendanceapp

import android.Manifest
import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class GeofenceBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == "GEOFENCE_ENTERED") {
            // Handle geofence entered event
            sendNotification(context, "Entered Geofence", "You've entered the geofence!")
        }
    }

    @SuppressLint("MissingPermission")
    private fun sendNotification(context: Context, title: String, message: String) {
        val builder = NotificationCompat.Builder(context, "channel_id")
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(0, builder.build())
    }
}