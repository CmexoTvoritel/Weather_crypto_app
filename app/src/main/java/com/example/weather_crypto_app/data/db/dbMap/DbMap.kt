package com.example.weather_crypto_app.data.db.dbMap

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.weather_crypto_app.data.names.city.PointCity

@Entity(tableName = "map_table")
data class DbMap(
    @PrimaryKey(autoGenerate = true)
    val uid: Int,
    val CityName: String,
    val ruCityName: String,
    val lan: Double,
    val lon: Double,
)
