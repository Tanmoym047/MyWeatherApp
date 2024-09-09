package com.example.myweatherapp.data.user

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myweatherapp.data.weatherHistory.WeatherHistory
import com.example.myweatherapp.data.weatherHistory.WeatherHistoryDao


@Database(entities = [User::class, WeatherHistory::class], version = 2, exportSchema = false)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun weatherHistoryDao(): WeatherHistoryDao
}
