package com.example.myweatherapp.data.weatherHistory

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface WeatherHistoryDao {
    @Insert
    suspend fun insertWeather(weather: WeatherHistory): Long

    @Query("SELECT * FROM weather_history ORDER BY timestamp DESC")
    suspend fun getAllWeatherHistory(): List<WeatherHistory>
}
