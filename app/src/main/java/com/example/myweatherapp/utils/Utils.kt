package com.example.myweatherapp.utils

import java.sql.Timestamp
import java.text.SimpleDateFormat

class Utils {
    companion object{
        fun timestampToHumanData(timestamp: Long, format: String): String{
            val sdf = SimpleDateFormat(format)
            return sdf.format(timestamp * 1000)
        }

        fun buildIcon(icon: String, isBigSize: Boolean = true): String{
            return if(isBigSize){
                "https://openweathermap.org/img/wn/$icon@4x.png"
            }
            else{
                "https://openweathermap.org/img/wn/$icon.png"
            }
        }
    }
}