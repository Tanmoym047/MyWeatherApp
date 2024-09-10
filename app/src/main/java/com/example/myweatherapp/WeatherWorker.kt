package com.example.myweatherapp

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.myweatherapp.data.weatherHistory.WeatherHistory
import com.example.myweatherapp.model.Weather.WeatherResult
import com.example.myweatherapp.network.RetrofitClient
import com.example.myweatherapp.viewmodel.MainViewModel
import retrofit2.HttpException

class WeatherWorker(context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {

    var weatherHistoryRepository = Graph.weatherHistoryRepository
//    val viewModel = MainViewModel()

//    init {
//        // Initialize the database and repository
//        val db = AppDatabase.getDatabase(context) // Reuse the existing AppDatabase instance
//        weatherHistoryRepository = WeatherHistoryRepository(db.weatherHistoryDao())
//    }

    override suspend fun doWork(): Result {
        // Fetch weather data from OpenWeatherMap API
        try {
            val weatherData = fetchWeatherData("Rajshahi")


            // Insert the weather data into the Room database
            val weatherHistory = WeatherHistory(
                city = weatherData.city,
                temperature = weatherData.temperature,
                condition = weatherData.condition,
                timestamp = System.currentTimeMillis()
            )
            weatherHistoryRepository.insertWeatherHistory(weatherHistory)
            return Result.success()
        }
        catch (e: Exception){
            return Result.failure()
        }

    }

    private suspend fun fetchWeatherData(city: String): WeatherHistory {
        val apiService = RetrofitClient.getInstance()
        return try {
            val apiResponse = apiService.getWeatherByCity(city)

            // Convert API response to WeatherData class
            WeatherHistory(
                city = apiResponse.name ?: "null",
                temperature = apiResponse.main?.temp ?: 0.0,
                condition = apiResponse.weather?.get(0)?.description ?: "null" ,
                timestamp = System.currentTimeMillis()// Getting description from first element in weather array
            )
        } catch (e: HttpException) {
            // Handle exception in case of failure (e.g., city not found, network issues)
            WeatherHistory(city = "Unknown", temperature = 0.0, condition = "Unknown", timestamp = System.currentTimeMillis())
        }
    }
}
