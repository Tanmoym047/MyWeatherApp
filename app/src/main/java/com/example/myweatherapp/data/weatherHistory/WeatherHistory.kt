package com.example.myweatherapp.data.weatherHistory

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather_history")
data class WeatherHistory(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val city: String,
    val temperature: Double,
    val condition: String,
    val timestamp: Long
)
