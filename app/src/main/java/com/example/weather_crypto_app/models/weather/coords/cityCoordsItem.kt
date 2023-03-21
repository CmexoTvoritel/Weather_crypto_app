package com.example.weather_crypto_app.models.weather.coords

data class cityCoordsItem(
    val country: String,
    val lat: Double,
    val local_names: LocalNames,
    val lon: Double,
    val name: String,
    val state: String
)