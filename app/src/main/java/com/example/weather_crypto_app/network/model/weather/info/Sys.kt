package com.example.weather_crypto_app.network.model.weather.info

data class Sys(
    val country: String,
    val id: Int,
    val sunrise: Int,
    val sunset: Int,
    val type: Int
)