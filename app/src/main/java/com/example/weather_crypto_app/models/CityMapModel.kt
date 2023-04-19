package com.example.weather_crypto_app.models

import com.example.weather_crypto_app.data.names.city.PointCity

data class CityMapModel (
        val shortName: String,
        val fullNameCity: String,
        val nameApiCity: String,
        val pointCity: PointCity,
)