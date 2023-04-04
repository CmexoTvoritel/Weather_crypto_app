package com.example.weather_crypto_app.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [DbCrypto::class], version = 1)
abstract class CryptoDataBase: RoomDatabase() {
    abstract fun dbDao(): DbDao
}