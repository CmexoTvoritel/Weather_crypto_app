package com.example.weather_crypto_app.presentation.model

import com.example.weather_crypto_app.data.model.city.PointCity

data class CityMapModel (
    val shortName: String,
    val fullNameCity: String,
    val nameApiCity: String,
    val pointCity: PointCity,
)