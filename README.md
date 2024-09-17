![Weather App Screenshot](app/src/main/res/mipmap-xxxhdpi/ic_launcher.webp)
<div align="center">
<img src="app/src/main/res/mipmap-xxxhdpi/ic_launcher.webp" alt="Weather App Icon" width=25% height=25%>
  <h1 align="center">MyWeatherApp</h1>
  <p align="center">A Kotlin Weather app</p>
</div>

An Android weather application built using Jetpack Compose, MVVM architecture, Retrofit for API calls to OpenWeatherMapApi, and Room for SQLite database. The app allows users to register and log in, fetch weather updates, store history, and display real-time notifications.

## Features

- **User Registration and Login**: Users can create an account and log in.
- **Weather Information**: Displays real-time Weather and Forecast using the OpenWeatherMap API.
- **Weather History**: Weather data is stored in SQLite database (Room) every hour and can be viewed in a history screen.
- **Persistent Notification**: A notification showing current weather data is updated every hour.

## Technologies Used

- **Jetpack Compose**: For the UI
- **ViewModel**: For mVVM Architecture
- **Retrofit**: For network requests to OpenWeatherMap API
- **Room Database (SQLite)**: For local database storage
- **WorkManager**: To fetch and update weather data in the background every 1h

## How It Works

### 1. User Registration and Login
- The app stores user credentials in the SQLite database via Room. Users can register and then log in with their username and password.

### 2. Fetching Weather Data
- Weather data is fetched from the OpenWeatherMap API using Retrofit.

### 3. Storing Weather History
- Every hour, the app fetches the latest weather data and stores it in the SQLite database. The data is then viewable from the history section in the app's drawer menu.

### 4. Notifications
- A notification is triggered when the app is opened and updated every hour with the latest weather data. The notification persists until the app is closed.

## Screenshots
<div align="center">
    <img src="screenshots/Screenshot_20240918_022033_MyWeatherApp.jpg" alt="Movies app preview" width=25% height=25%>
    <img src="screenshots/Screenshot_20240918_022040_MyWeatherApp.jpg" alt="Movies app preview" width=25% height=25%>
    <img src="screenshots/Screenshot_20240918_022057_MyWeatherApp.jpg" alt="Movies app preview" width=25% height=25%>
    <img src="screenshots/Screenshot_20240918_022210_MyWeatherApp.jpg" alt="Movies app preview" width=25% height=25%>
    <img src="screenshots/Screenshot_20240918_022221_MyWeatherApp.jpg" alt="Movies app preview" width=25% height=25%>
  
</div>


