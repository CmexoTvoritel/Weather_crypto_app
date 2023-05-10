package com.example.weather_crypto_app.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather_table")
data class DbWeather(
    @PrimaryKey(autoGenerate = true)
    val uid: Int,
    val CityName: String,
    val ruCityName: String,
)
