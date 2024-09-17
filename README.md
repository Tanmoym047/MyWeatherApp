![Weather App Screenshot](app/src/main/res/mipmap-xxxhdpi/ic_launcher.webp)
# MyWeatherApp

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

Add screenshots of your app here.


## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

