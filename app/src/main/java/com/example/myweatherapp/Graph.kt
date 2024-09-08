package com.example.myweatherapp

import android.content.Context
import androidx.room.Room
import com.example.myweatherapp.data.user.UserDatabase
import com.example.myweatherapp.data.user.UserRepository

object Graph {
    lateinit var database: UserDatabase

//    val db = Room.databaseBuilder(
//        applicationContext,
//        UserDatabase::class.java, "weather-app-database"
//    ).build()

    val userRepository by lazy {
        UserRepository(userDao = database.userDao())
    }

    fun provide(context: Context){
        database = Room.databaseBuilder(
            context = context,
            UserDatabase::class.java,
            "user.db").build()
    }
}