package com.example.myweatherapp

import android.content.Context
import androidx.room.Room
import com.example.myweatherapp.data.user.UserDatabase
import com.example.myweatherapp.data.user.UserRepository
import com.example.myweatherapp.data.weatherHistory.WeatherHistoryRepository

object Graph {
    lateinit var database: UserDatabase

    val userRepository by lazy {
        UserRepository(userDao = database.userDao())
    }
    val weatherHistoryRepository by lazy {
        WeatherHistoryRepository(weatherHistoryDao = database.weatherHistoryDao())
    }

    fun provide(context: Context){
        database = Room.databaseBuilder(
            context = context,
            UserDatabase::class.java,
            "weather-app-database").build()
    }
}