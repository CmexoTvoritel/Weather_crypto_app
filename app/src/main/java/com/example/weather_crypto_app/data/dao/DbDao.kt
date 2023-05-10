package com.example.weather_crypto_app.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.weather_crypto_app.data.model.DbCrypto

@Dao
interface DbDao {
    @Query("SELECT * FROM crypto_table ORDER BY uid ASC")
    fun getAll(): LiveData<List<DbCrypto>>

    @Insert
    fun insertCoin(vararg dbCrypto: DbCrypto)

    @Delete
    fun delete(dbCrypto: DbCrypto)
}