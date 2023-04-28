package com.example.weather_crypto_app.models.weather

data class WeatherMenuModel(
    var name_city: String = "",
    var temp: Double = 0.00,
    var feel_temp: Double = 0.00,
    var name_weather: String = "",
    var icon: String = "",
    var wind: Double = 0.00,
    var pressure: Double = 0.00,
    var wetness: Int = 0,
    var cloud: Int = 0,
    var visibility_weather: Double = 0.00,
    var min_temp: Double = 0.00,
    var max_temp: Double = 0.00,
)
