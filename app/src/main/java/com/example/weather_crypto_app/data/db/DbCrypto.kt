package com.example.weather_crypto_app.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "crypto_table")
data class DbCrypto(
    @PrimaryKey(autoGenerate = true)
    val uid: Int,
    val nameCoin: String,
    val image: String,
    val costCoin: Double,
)
