package com.example.myweatherapp.data.weatherHistory

class WeatherHistoryRepository(private val weatherHistoryDao: WeatherHistoryDao) {
    suspend fun insertWeatherHistory(weather: WeatherHistory) {
        weatherHistoryDao.insertWeather(weather)
    }

    suspend fun getWeatherHistory(): List<WeatherHistory> {
        return weatherHistoryDao.getAllWeatherHistory()
    }
}
