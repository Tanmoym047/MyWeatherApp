package com.example.myweatherapp.model.Forecast

import com.example.myweatherapp.model.Weather.Clouds
import com.example.myweatherapp.model.Weather.Coord
import com.example.myweatherapp.model.Weather.Main
import com.example.myweatherapp.model.Weather.Weather
import com.example.myweatherapp.model.Weather.Wind
import com.google.gson.annotations.SerializedName

data class ForecastResult(
    @SerializedName("cod") var cod: String? = null,
    @SerializedName("message") var message: String? = null,
    @SerializedName("cnt") var cnt: Int? = null,
    @SerializedName("list") var list: ArrayList<CustomList>? = arrayListOf(),
    @SerializedName("city") var city: City? = City(),
    @SerializedName("wind") var wind: Wind? = Wind(),
    @SerializedName("clouds") var clouds: Clouds? = Clouds()
)
