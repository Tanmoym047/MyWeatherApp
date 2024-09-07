package com.example.myweatherapp.constant

class Const {
    companion object {
        val permissions = arrayOf(
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION,
        )
        const val openWeatherMapApiKey = "53937e78d46bfc11f6a773b1422b7b86"
        const val colorBg1 = 0xff08203e
        const val colorBg2 = 0xff557c93
        const val cardColor = 0xFF333639
        const val LOADING = "Loading..."
        const val NA = "N/A"
    }
}