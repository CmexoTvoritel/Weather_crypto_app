package com.example.weather_crypto_app.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "menu_table")
data class DbMenu(
    @PrimaryKey(autoGenerate = true)
    val uid: Int,
    val MenuName: String,
)
