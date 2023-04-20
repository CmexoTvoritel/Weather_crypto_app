package com.example.weather_crypto_app.models.weather

data class WeatherMenuModel(
    var name_city: String,
    var temp: Double,
    var feel_temp: Double,
    var name_weather: String,
    var icon: String,
    var wind: Double,
    var pressure: Double,
    var wetness: Int,
    var cloud: Int,
    var visibility_weather: Double,
    var min_temp: Double,
    var max_temp: Double,
)
