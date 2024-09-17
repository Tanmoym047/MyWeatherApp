package com.example.myweatherapp

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.myweatherapp.data.weatherHistory.WeatherHistory
import com.example.myweatherapp.network.RetrofitClient
import retrofit2.HttpException

class WeatherWorker(context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {

    var weatherHistoryRepository = Graph.weatherHistoryRepository
    val weatherNotification = WeatherNotification()

    override suspend fun doWork(): Result {
        // Fetch weather data from OpenWeatherMap API
        try {
            val weatherData = fetchWeatherData("Rajshahi")

            // notification for weather
            weatherNotification.showWeatherNotification(
                applicationContext,
                weatherData.temperature,
                weatherData.condition
            )

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
