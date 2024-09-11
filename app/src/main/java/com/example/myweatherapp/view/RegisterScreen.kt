package com.example.myweatherapp.view

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myweatherapp.constant.Const.Companion.colorBg1
import com.example.myweatherapp.constant.Const.Companion.colorBg2
import com.example.myweatherapp.navigation.Screen
import com.example.myweatherapp.viewmodel.MainViewModel

@Composable
fun RegisterScreen(navController: NavController, mainViewModel: MainViewModel) {
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val context = LocalContext.current

    val gradient = Brush.linearGradient(
        colors = listOf(Color(colorBg1), Color(colorBg2)),
        start = Offset(1000f, -1000f),
        end = Offset(1000f, 1000f)
    )

    Column(
        modifier = Modifier
            .background(gradient)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextField(value = username, onValueChange = { username = it }, label = { Text("Username", color = Color.White) })
        Spacer(modifier = Modifier.padding(bottom = 8.dp))

        TextField(value = email, onValueChange = { email = it }, label = { Text("Email", color = Color.White) })
        Spacer(modifier = Modifier.padding(bottom = 8.dp))

        TextField(value = password, onValueChange = { password = it }, label = { Text("Password", color = Color.White) }, visualTransformation = PasswordVisualTransformation())

        Button(
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.DarkGray),
            onClick = {
                mainViewModel.register(username, email, password)
                Toast.makeText(context, "Registration Successful", Toast.LENGTH_SHORT).show()
                navController.navigate(Screen.Login.route)
            }
        ) {
            Text("Register", color = Color.White)
        }

        TextButton(
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.DarkGray),
            onClick = { navController.navigate(Screen.Login.route) }) {
            Text("Already have an account? Login", color = Color.White)
        }
    }
}
