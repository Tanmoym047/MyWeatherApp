package com.example.myweatherapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Looper
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myweatherapp.constant.Const.Companion.colorBg1
import com.example.myweatherapp.constant.Const.Companion.colorBg2
import com.example.myweatherapp.constant.Const.Companion.permissions
import com.example.myweatherapp.model.LatLng
import com.example.myweatherapp.navigation.Screen
import com.example.myweatherapp.ui.theme.MyWeatherAppTheme
import com.example.myweatherapp.view.ErrorScreen
import com.example.myweatherapp.view.ForecastSection
import com.example.myweatherapp.view.LoginScreen
import com.example.myweatherapp.view.RegisterScreen
import com.example.myweatherapp.view.WeatherSection
import com.example.myweatherapp.viewmodel.MainViewModel
import com.example.myweatherapp.viewmodel.STATE
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    private lateinit var mainViewModel: MainViewModel
    private var locationRequired: Boolean = false

    override fun onResume() {
        super.onResume()
        if(locationRequired) startLocationUpdate()
    }


    @SuppressLint("MissingPermission")
    private fun startLocationUpdate() {
        locationCallback?.let {
            val locationRequest = LocationRequest.Builder(
                Priority.PRIORITY_HIGH_ACCURACY, 100
            )
                .setWaitForAccurateLocation(false)
                .setMinUpdateIntervalMillis(3000)
                .setMaxUpdateDelayMillis(100)
                .build()
            fusedLocationProviderClient?.requestLocationUpdates(
                locationRequest, it, Looper.getMainLooper()
            )

        }
    }

    override fun onPause() {
        super.onPause()
        locationCallback?.let {
            fusedLocationProviderClient?.removeLocationUpdates(it)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initLocationClient()
        initViewModel()
//        enableEdgeToEdge()
        setContent {
            var currentLocation by remember {
                mutableStateOf(LatLng(0.0, 0.0))
            }

            val scaffoldState = rememberScaffoldState()
            val scope = rememberCoroutineScope()

            locationCallback = object: LocationCallback(){
                override fun onLocationResult(p0: LocationResult) {
                    super.onLocationResult(p0)
                    for(location in p0.locations){
                        currentLocation = LatLng(
                            location.latitude,
                            location.longitude
                        )
                    }
                }
            }

            MyWeatherAppTheme {
                Scaffold (
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            backgroundColor = Color.DarkGray,
                            title = { Text(text = "MyWeatherApp", color = Color.White) },
                            navigationIcon = {
                                IconButton(
                                    onClick = {
                                        scope.launch {
                                            scaffoldState.drawerState.open()
                                        }
                                    }
                                ) {
                                    Icon(imageVector = Icons.Default.Menu, contentDescription = "Hamburger Menu", tint = Color.White)
                                }
                            }
                        )
                    },

                ){
                    padding ->
                    Navigation(context = this@MainActivity, currentLocation = currentLocation, viewModel = MainViewModel(), padding)

                }
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colorScheme.background
//                ) {
////                    HomeScreen(this@MainActivity ,currentLocation, viewModel = MainViewModel())
//                    Navigation(context = this@MainActivity, currentLocation = currentLocation, viewModel = MainViewModel())
//                }
            }
        }
    }

    private fun fetchWeatherInformation(mainViewModel: MainViewModel, currentLocation: LatLng) {
        mainViewModel.state = STATE.LOADING
        mainViewModel.getWeatherByLocation(currentLocation)
        mainViewModel.getForecastByLocation(currentLocation)
        mainViewModel.state = STATE.SUCCESS
    }
    private fun fetchWeatherInformationByCity(mainViewModel: MainViewModel) {
        mainViewModel.state = STATE.LOADING
        mainViewModel.getWeatherByCity()
        mainViewModel.getForecastByCity()
        mainViewModel.state = STATE.SUCCESS
    }

    private fun initViewModel() {
        mainViewModel = ViewModelProvider(this@MainActivity)[MainViewModel::class.java]
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun HomeScreen(context: Context, currentLocation: LatLng, viewModel: MainViewModel) {
        val launcherMultiplePermissions = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestMultiplePermissions()
        ) {
            permissionMap ->
                val areGranted = permissionMap.values.reduce{
                    accepted, next -> accepted && next
                }
            if(areGranted){
                locationRequired = true
                startLocationUpdate()
                Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
            }

        }

        val systemUiController = rememberSystemUiController()
        
        DisposableEffect(
            key1 = true,
            effect = {
                systemUiController.isSystemBarsVisible = false
                onDispose {
                    systemUiController.isSystemBarsVisible = true
                }
            }
        )

        LaunchedEffect(
            key1 = true,
            block = {
                fetchWeatherInformation(mainViewModel, currentLocation)
            }
        )

        LaunchedEffect(
            key1 = currentLocation,
            block = {
                coroutineScope {
                    if(permissions.all{
                        ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
                        }){
                        startLocationUpdate()
                    }
                    else{
                        launcherMultiplePermissions.launch(permissions)
                    }
                }
            }
        )
        


        val gradient = Brush.linearGradient(
            colors = listOf(Color(colorBg1), Color(colorBg2)),
            start = Offset(1000f, -1000f),
            end = Offset(1000f, 1000f)
        )
        
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(gradient),
            contentAlignment = Alignment.BottomCenter
        ){
            val screenHeight = LocalConfiguration.current.screenHeightDp.dp
            val marginTop = screenHeight * 0.005f
            val marginTopPx = with(LocalDensity.current){marginTop.toPx()}

            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .layout { measurable, constraints ->
                        val placeable = measurable.measure((constraints))
                        layout(
                            placeable.width,
                            placeable.height + marginTopPx.toInt()
                        ) {
                            placeable.placeRelative(0, marginTopPx.toInt())
                        }
                    },
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Created By: Tanmoy Mridha", modifier = Modifier.padding(bottom = 8.dp), color = Color.White, fontSize = 16.sp)
                Row (
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 16.dp)

                ){
                    OutlinedTextField(
                        modifier = Modifier.padding(top = 16.dp, bottom = 16.dp),
                        value = mainViewModel.city,
                        onValueChange = {
                            mainViewModel.onChangeCity(it)
                        },
                        textStyle = TextStyle(color = Color.White),
                        label = { Text(text = "City", color = Color.White)},
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = Color.White,
                            unfocusedBorderColor = Color.White
                        )

                    )
                    Spacer(modifier = Modifier.padding(horizontal = 4.dp))
                    Button(
                        colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray),
                        shape = RoundedCornerShape(23.dp),
                        onClick = {
                            fetchWeatherInformationByCity(mainViewModel)
                        }
                    ) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Search", tint = Color.White)
                    }
                }
                


                when (mainViewModel.state) {
                    STATE.LOADING -> {
                        LoadingSection()
                    }
                    STATE.FAILED -> {
                        ErrorSection(mainViewModel.errorMessage)
                    }
                    else -> {
                        WeatherSection(mainViewModel.weatherResponse)
                        ForecastSection(mainViewModel.forecastResponse)
                    }
                }
            }

            Column {
                FloatingActionButton(
                    onClick = {
                        // fetch Api when button is pressed
                        fetchWeatherInformation(mainViewModel, currentLocation)
                    },
                    modifier = Modifier.padding(bottom = 16.dp),
                    elevation = FloatingActionButtonDefaults.elevation(8.dp)
                ) {
                    Icon(imageVector = Icons.Default.Refresh, contentDescription = "Add")
                }
            }

        }
    }

    @Composable
    fun ErrorSection(errorMessage: String) {
        return Column (
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(text = errorMessage, color = Color.Red)
        }
    }

    @Composable
    fun LoadingSection() {
        return Column (
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            CircularProgressIndicator(color = Color.White)
        }
    }

    @Composable
    fun Navigation(context: Context, currentLocation: LatLng, viewModel: MainViewModel, padding: PaddingValues){
        val navController = rememberNavController()

        NavHost(navController = navController, startDestination = Screen.Login.route) {
            composable(Screen.Login.route) {
                LoginScreen(navController, mainViewModel)
            }
            composable(Screen.Register.route) {
                RegisterScreen(navController, mainViewModel)
            }
            composable(Screen.Home.route) {
                HomeScreen(context, currentLocation, mainViewModel)
            }
            composable(Screen.Error.route) {
                ErrorScreen()
            }
        }
    }

    private fun initLocationClient() {
        fusedLocationProviderClient = LocationServices
            .getFusedLocationProviderClient(this)
    }
}