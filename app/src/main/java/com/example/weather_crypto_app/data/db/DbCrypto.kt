package com.example.weather_crypto_app.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DbCrypto(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "name_coin") val nameCoin: String,
    @ColumnInfo(name = "cost_coin") val costCoin: Double,
)
