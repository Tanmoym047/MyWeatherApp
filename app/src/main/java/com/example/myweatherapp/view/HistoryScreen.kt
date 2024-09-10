package com.example.myweatherapp.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.example.myweatherapp.constant.Const.Companion.colorBg1
import com.example.myweatherapp.constant.Const.Companion.colorBg2
import com.example.myweatherapp.navigation.Screen
import com.example.myweatherapp.viewmodel.MainViewModel
import java.util.Date

@Composable
fun HistoryScreen() {
    val viewModel = MainViewModel()
    // Observe the weather history LiveData
    val weatherHistory by viewModel.loadWeatherHistory().observeAsState(initial = emptyList())
    val navController = rememberNavController()
    // Display the weather history using LazyColumn

    val gradient = Brush.linearGradient(
        colors = listOf(Color(colorBg1), Color(colorBg2)),
        start = Offset(1000f, -1000f),
        end = Offset(1000f, 1000f)
    )

    LazyColumn (
        modifier = Modifier
            .background(gradient)
            .fillMaxSize(),
    ){
        items(weatherHistory) {
            weather ->
            Column(modifier = Modifier.padding(16.dp)) {
                Text("City: ${weather.city}", color = Color.White, fontSize = 16.sp)
                Text("Temperature: ${weather.temperature}°C",color = Color.White , fontSize = 16.sp)
                Text("Condition: ${weather.condition}", color = Color.White, fontSize = 16.sp)
                Text("Time: ${Date(weather.timestamp)}", color = Color.White, fontSize = 16.sp)
                HorizontalDivider(modifier = Modifier.padding(top = 16.dp), thickness = 2.dp, color = Color.White)
            }
        }
    }
}

