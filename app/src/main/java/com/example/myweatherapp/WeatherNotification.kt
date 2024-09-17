package com.example.myweatherapp

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat

class WeatherNotification {

    fun showWeatherNotification(context: Context, temperature: Double, description: String) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val notification = NotificationCompat.Builder(context, "weather_channel")
            .setSmallIcon(R.drawable.ic_weather) // Your app icon
            .setContentTitle("Rajshahi")
            .setContentText("$temperature°C  •  $description")
            .setOngoing(true) // Makes it a persistent notification
            .setPriority(NotificationCompat.PRIORITY_LOW) // Ensures it's persistent but not intrusive
            .build()

        notificationManager.notify(1, notification) // Notify with a unique ID
    }
}
