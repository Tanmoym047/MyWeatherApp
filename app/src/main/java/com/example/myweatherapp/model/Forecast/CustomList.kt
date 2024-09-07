package com.example.myweatherapp.model.Forecast

import com.example.myweatherapp.model.Weather.Clouds
import com.example.myweatherapp.model.Weather.Main
import com.example.myweatherapp.model.Weather.Sys
import com.example.myweatherapp.model.Weather.Weather
import com.example.myweatherapp.model.Weather.Wind
import com.google.gson.annotations.SerializedName

data class CustomList(
    @SerializedName("dt") var dt: Int? = null,
    @SerializedName("main") var main: Main? = Main(),
    @SerializedName("weather") var weather: ArrayList<Weather>? = arrayListOf(),
    @SerializedName("clouds") var clouds: Clouds? = Clouds(),
    @SerializedName("wind") var wind: Wind? = Wind(),
    @SerializedName("visibility") var visibility: Int? = null,
    @SerializedName("pop") var pop: Double? = null,
    @SerializedName("sys") var sys: Sys? = Sys(),
    @SerializedName("dt_txt") var dtTxt: String? = null,
)
