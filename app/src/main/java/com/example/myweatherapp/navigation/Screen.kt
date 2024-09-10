package com.example.myweatherapp.navigation

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Register : Screen("register")
    object Home : Screen("home")
    object Error : Screen("error")
    object History : Screen("history")


}

