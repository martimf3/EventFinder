package com.example.eventfinder.notifications

import android.Manifest
import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.example.eventfinder.MainActivity
import com.example.eventfinder.R

class Notifications (context: Context) {
    private val context = context
    private val CHANNEL_ID = "channel_id_0"
    private val notificationId = 101

    private fun createNotificationChannel(title : String, descriptionText : String){
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL_ID, title, importance).apply {
            description = descriptionText
        }
        val notificationManager : NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    private fun sendNotification(title: String, descriptionText: String, mainImage: Int, largeIcon: Int ) {
        createNotificationChannel(title, descriptionText)

        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val bitmap = BitmapFactory.decodeResource(context.resources, mainImage)
        val bitmapLargeIcon = BitmapFactory.decodeResource(context.resources, largeIcon)

        // Check if the VIBRATE permission is granted
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.VIBRATE)
            == PackageManager.PERMISSION_GRANTED) {
            val builder = NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(title)
                .setContentText(descriptionText)
                .setLargeIcon(bitmapLargeIcon)
                .setStyle(NotificationCompat.BigPictureStyle().bigPicture(bitmap))
                //.setStyle(NotificationCompact.BigTextStyle().bigText("The text we hant to show below on the notification instead of the photo"))
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

            with(NotificationManagerCompat.from(context)) {
                notify(notificationId, builder.build())
            }
        } else {
            // Request the VIBRATE permission
            ActivityCompat.requestPermissions(
                context as Activity, arrayOf(Manifest.permission.VIBRATE),
                1
            )
        }
    }
}