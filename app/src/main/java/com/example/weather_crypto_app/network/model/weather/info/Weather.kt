package com.example.weather_crypto_app.network.model.weather.info

data class Weather(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)