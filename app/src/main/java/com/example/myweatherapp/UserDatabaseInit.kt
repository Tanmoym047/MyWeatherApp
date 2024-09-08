package com.example.myweatherapp

import android.app.Application

class UserDatabaseInit: Application() {
    override fun onCreate() {
        super.onCreate()
        Graph.provide(this)
    }
}