package com.example.myweatherapp.network

import com.example.myweatherapp.constant.Const.Companion.openWeatherMapApiKey
import com.example.myweatherapp.model.Forecast.ForecastResult
import com.example.myweatherapp.model.Weather.WeatherResult
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("weather")
    suspend fun getWeather(
        @Query("lat") lat: Double = 0.0,
        @Query("lon") lng: Double = 0.0,
//        @Query("q") name: String = "dhaka",
        @Query("units") units: String = "metric",
        @Query("appid") appId: String = openWeatherMapApiKey,
    ): WeatherResult

    @GET("forecast")
    suspend fun getForecast(
        @Query("lat") lat: Double = 0.0,
        @Query("lon") lng: Double = 0.0,
//        @Query("q") name: String = "dhaka",
        @Query("units") units: String = "metric",
        @Query("appid") appId: String = openWeatherMapApiKey,
    ): ForecastResult
}