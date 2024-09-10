package com.example.myweatherapp.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.myweatherapp.constant.Const.Companion.LOADING
import com.example.myweatherapp.constant.Const.Companion.NA
import com.example.myweatherapp.model.Weather.WeatherResult
import com.example.myweatherapp.utils.Utils.Companion.buildIcon
import com.example.myweatherapp.utils.Utils.Companion.timestampToHumanData
import com.guru.fontawesomecomposelib.FaIcon
import com.guru.fontawesomecomposelib.FaIconType
import com.guru.fontawesomecomposelib.FaIcons

@Composable
fun WeatherSection(weatherResponse: WeatherResult) {
    // title
    var title = ""
    if(!weatherResponse.name.isNullOrEmpty()){
        weatherResponse?.name?.let {
            title = it
        }
    }
    else{
        weatherResponse.coord?.let {
            title = "${it.lat}/${it.lon}"
        }
    }

    // subtitle (date)
    var subTitle = ""
    val dateVal = (weatherResponse.dt ?: 0)
    subTitle = if(dateVal == 0) LOADING else timestampToHumanData(dateVal.toLong(), "dd-MM-yyyy")
    if(!weatherResponse.name.isNullOrEmpty()){
        weatherResponse?.name?.let {
            title = it
        }
    }
    else{
        weatherResponse.coord?.let {
            title = "${it.lat}/${it.lon}"
        }
    }

    // icon
    var icon = ""
    var description = ""
    weatherResponse.weather.let {
        if(it!!.size > 0){
            description = if(it[0].description == null) LOADING else it[0].description!!
            icon = if(it[0].icon == null) LOADING else it[0].icon!!
        }
    }

    // Temp
    var temp = ""
    weatherResponse.main?.let {
        temp = "${it.temp} C"
    }

    // wind
    var wind = ""
    weatherResponse.wind?.let {
        wind = if (it == null) LOADING else "${it.speed}"
    }

    // clouds
    var clouds = ""
    weatherResponse.clouds?.let {
        clouds = if (it == null) LOADING else "${it.all}"
    }

    // snow
    var snow = ""
    weatherResponse.snow?.let {
        snow = if (it.d1h == null) NA else "${it.d1h}"
    }

    WeatherTitleSection(text = title, subText = subTitle, fontsize = 30.sp)
    WeatherImage(icon = icon)
    WeatherTitleSection(text = temp, subText = description, fontsize = 40.sp)
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceAround,
    ){
        WeatherInfo(icon = FaIcons.Wind, text = wind)
        WeatherInfo(icon = FaIcons.Cloud, text = clouds)
        WeatherInfo(icon = FaIcons.Snowflake, text = snow)
    }
}

@Composable
fun WeatherInfo(icon: FaIconType.SolidIcon, text: String) {
    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ){
        FaIcon(faIcon = icon, size = 36.dp, tint = Color.White, modifier = Modifier.padding(bottom = 4.dp))
        Text(text = text, fontSize = 20.sp, color = Color.White)
        
    }
}

@Composable
fun WeatherImage(icon: String) {
    AsyncImage(
        model = buildIcon(icon),
        contentDescription = icon,
        modifier = Modifier
            .width(180.dp)
            .height(180.dp),
        contentScale = ContentScale.FillBounds
    )
}

@Composable
fun WeatherTitleSection(text: String, subText: String, fontsize: TextUnit) {
    Column (
        modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(text = text, color = Color.White, fontSize = fontsize, fontWeight = FontWeight.Bold )
        Text(text = subText, color = Color.White, fontSize = 18.sp )
    }
}
