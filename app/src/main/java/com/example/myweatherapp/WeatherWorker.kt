package com.example.myweatherapp

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.myweatherapp.data.weatherHistory.WeatherHistory
import com.example.myweatherapp.viewmodel.MainViewModel

class WeatherWorker(context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {

    var weatherHistoryRepository = Graph.weatherHistoryRepository
    val viewModel = MainViewModel()

//    init {
//        // Initialize the database and repository
//        val db = AppDatabase.getDatabase(context) // Reuse the existing AppDatabase instance
//        weatherHistoryRepository = WeatherHistoryRepository(db.weatherHistoryDao())
//    }

    override suspend fun doWork(): Result {
        // Fetch weather data from OpenWeatherMap API
        viewModel.getWeatherByLocation(viewModel.currentLocation)
        val weatherData = viewModel.weatherResponse

        // Insert the weather data into the Room database
        weatherHistoryRepository.insertWeatherHistory(
            WeatherHistory(
                city = weatherData.name ?: "null",
                temperature = weatherData.main?.temp ?: 0.0,
                condition = weatherData.weather.toString(),
                timestamp = System.currentTimeMillis()
            )
        )
        return Result.success()
    }
}
