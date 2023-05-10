package com.example.weather_crypto_app.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "map_table")
data class DbMap(
    @PrimaryKey(autoGenerate = true)
    val uid: Int,
    val CityName: String,
    val ruCityName: String,
    val lan: Double,
    val lon: Double,
)
