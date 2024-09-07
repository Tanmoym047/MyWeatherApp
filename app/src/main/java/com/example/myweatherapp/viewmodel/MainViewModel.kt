package com.example.myweatherapp.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myweatherapp.model.Forecast.ForecastResult
import com.example.myweatherapp.model.LatLng
import com.example.myweatherapp.model.Weather.WeatherResult
import com.example.myweatherapp.network.RetrofitClient
import kotlinx.coroutines.launch

enum class STATE{
    LOADING, SUCCESS, FAILED
}

class MainViewModel: ViewModel() {
    // control state
    var state by mutableStateOf(STATE.LOADING)
    var city by mutableStateOf("")

    // Weather API value hold
    var weatherResponse: WeatherResult by mutableStateOf(WeatherResult())

    // Forecast API value hold
    var forecastResponse: ForecastResult by mutableStateOf(ForecastResult())
    var errorMessage: String by mutableStateOf("")

    fun onChangeCity(cityName: String) {
        city = cityName
    }

//    fun getWeatherByCity(){
//        viewModelScope.launch{
//            state = STATE.LOADING
//            val apiService = RetrofitClient.getInstance()
//            try {
//                Log.d("API", "Calling")
//                val apiResponse = apiService.getWeather()
//                weatherResponse = apiResponse
//                state = STATE.SUCCESS
//            }
//            catch (e: Exception){
//                errorMessage = e.message!!.toString()
//                state = STATE.FAILED
//            }
//        }
//    }

    fun getWeatherByLocation(latLng: LatLng){
        viewModelScope.launch{
            state = STATE.LOADING
            val apiService = RetrofitClient.getInstance()
            try {
                Log.d("API", "Calling")
                val apiResponse = apiService.getWeather(latLng.lat, latLng.lng)
                weatherResponse = apiResponse
                state = STATE.SUCCESS
            }
            catch (e: Exception){
                errorMessage = e.message!!.toString()
                state = STATE.FAILED
            }
        }
    }

    fun getForecastByLocation(latLng: LatLng){
        viewModelScope.launch{
            state = STATE.LOADING
            val apiService = RetrofitClient.getInstance()
            try {
                val apiResponse = apiService.getForecast(latLng.lat, latLng.lng)
                forecastResponse = apiResponse
                state = STATE.SUCCESS
            }
            catch (e: Exception){
                errorMessage = e.message!!.toString()
                state = STATE.FAILED
            }
        }
    }

}