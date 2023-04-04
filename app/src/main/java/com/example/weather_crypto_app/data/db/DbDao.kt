package com.example.weather_crypto_app.data.db

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

interface DbDao {
    @Query("SELECT * FROM dbCrypto")
    fun getAll(): List<DbCrypto>

    @Insert
    fun insertAll(vararg dbCrypto: DbCrypto)

    @Delete
    fun delete(dbCrypto: DbCrypto)
}