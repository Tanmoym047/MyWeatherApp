//package com.example.myweatherapp.view
//
//import android.content.Context
//import android.content.pm.PackageManager
//import android.widget.Toast
//import androidx.activity.compose.rememberLauncherForActivityResult
//import androidx.activity.result.contract.ActivityResultContracts
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.rememberScrollState
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.foundation.verticalScroll
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.automirrored.filled.ArrowForward
//import androidx.compose.material.icons.filled.Refresh
//import androidx.compose.material3.Button
//import androidx.compose.material3.ButtonDefaults
//import androidx.compose.material3.FloatingActionButton
//import androidx.compose.material3.FloatingActionButtonDefaults
//import androidx.compose.material3.Icon
//import androidx.compose.material3.OutlinedTextField
//import androidx.compose.material3.Text
//import androidx.compose.material3.TextFieldDefaults
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.DisposableEffect
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.geometry.Offset
//import androidx.compose.ui.graphics.Brush
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.layout.layout
//import androidx.compose.ui.platform.LocalConfiguration
//import androidx.compose.ui.platform.LocalDensity
//import androidx.compose.ui.text.TextStyle
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.core.content.ContextCompat
//import com.example.myweatherapp.constant.Const.Companion.colorBg1
//import com.example.myweatherapp.constant.Const.Companion.colorBg2
//import com.example.myweatherapp.constant.Const.Companion.permissions
//import com.example.myweatherapp.model.LatLng
//import com.example.myweatherapp.viewmodel.MainViewModel
//import com.example.myweatherapp.viewmodel.STATE
//import com.google.accompanist.systemuicontroller.rememberSystemUiController
//import kotlinx.coroutines.coroutineScope
//
//@Composable
//private fun LocationScreen(context: Context, currentLocation: LatLng, viewModel: MainViewModel) {
//    val launcherMultiplePermissions = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.RequestMultiplePermissions()
//    ) {
//            permissionMap ->
//        val areGranted = permissionMap.values.reduce{
//                accepted, next -> accepted && next
//        }
//        if(areGranted){
//            locationRequired = true
//            startLocationUpdate()
//            Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show()
//        }
//        else{
//            Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
//        }
//
//    }
//
//    val systemUiController = rememberSystemUiController()
//
//    DisposableEffect(
//        key1 = true,
//        effect = {
//            systemUiController.isSystemBarsVisible = false
//            onDispose {
//                systemUiController.isSystemBarsVisible = true
//            }
//        }
//    )
//
//    LaunchedEffect(
//        key1 = true,
//        block = {
//            fetchWeatherInformation(mainViewModel, currentLocation)
//        }
//    )
//
//    LaunchedEffect(
//        key1 = currentLocation,
//        block = {
//            coroutineScope {
//                if(permissions.all{
//                        ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
//                    }){
//                    startLocationUpdate()
//                }
//                else{
//                    launcherMultiplePermissions.launch(permissions)
//                }
//            }
//        }
//    )
//
//
//
//    val gradient = Brush.linearGradient(
//        colors = listOf(Color(colorBg1), Color(colorBg2)),
//        start = Offset(1000f, -1000f),
//        end = Offset(1000f, 1000f)
//    )
//
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(gradient),
//        contentAlignment = Alignment.BottomCenter
//    ){
//        val screenHeight = LocalConfiguration.current.screenHeightDp.dp
//        val marginTop = screenHeight * 0.005f
//        val marginTopPx = with(LocalDensity.current){marginTop.toPx()}
//
//        Column(
//            modifier = Modifier
//                .verticalScroll(rememberScrollState())
//                .layout { measurable, constraints ->
//                    val placeable = measurable.measure((constraints))
//                    layout(
//                        placeable.width,
//                        placeable.height + marginTopPx.toInt()
//                    ) {
//                        placeable.placeRelative(0, marginTopPx.toInt())
//                    }
//                },
//            verticalArrangement = Arrangement.Center,
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Text(text = "Created By: Tanmoy Mridha", modifier = Modifier.padding(bottom = 8.dp), color = Color.White, fontSize = 16.sp)
//            Row (
//                verticalAlignment = Alignment.CenterVertically
//            ){
//                OutlinedTextField(
//                    modifier = Modifier.padding(top = 16.dp, bottom = 16.dp),
//                    value = mainViewModel.city,
//                    onValueChange = {
//                        mainViewModel.onChangeCity(it)
//                    },
//                    textStyle = TextStyle(color = Color.White),
//                    label = { Text(text = "City", color = Color.White) },
//                    colors = TextFieldDefaults.outlinedTextFieldColors(
//                        focusedBorderColor = Color.White,
//                        unfocusedBorderColor = Color.White)
//
//                )
//                Spacer(modifier = Modifier.padding(horizontal = 8.dp))
//                Button(
//                    colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray),
//                    shape = RoundedCornerShape(23.dp),
//                    onClick = {
//                        fetchWeatherInformationByCity(mainViewModel)
//                    }
//                ) {
//                    Icon(imageVector = Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Search")
//                }
//            }
//
//
//
//            when (mainViewModel.state) {
//                STATE.LOADING -> {
//                    LoadingSection()
//                }
//                STATE.FAILED -> {
//                    ErrorSection(mainViewModel.errorMessage)
//                }
//                else -> {
//                    WeatherSection(mainViewModel.weatherResponse)
//                    ForecastSection(mainViewModel.forecastResponse)
//                }
//            }
//        }
//
//        Column {
//            FloatingActionButton(
//                onClick = {
//                    // fetch Api when button is pressed
//                    fetchWeatherInformation(mainViewModel, currentLocation)
//                },
//                modifier = Modifier.padding(bottom = 16.dp),
//                elevation = FloatingActionButtonDefaults.elevation(8.dp)
//            ) {
//                Icon(imageVector = Icons.Default.Refresh, contentDescription = "Add")
//            }
//        }
//
//    }
//}