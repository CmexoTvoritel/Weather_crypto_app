package com.example.weather_crypto_app.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.weather_crypto_app.data.model.DbMap

@Dao
interface DbMapDao {
    @Query("SELECT * FROM map_table ORDER BY uid ASC")
    fun getAll(): LiveData<List<DbMap>>

    @Update
    suspend fun updateMap(dbMap: DbMap)

    @Insert
    suspend fun insertMap(vararg dbMap: DbMap)

    @Delete
    fun deleteMap(dbMap: DbMap)
}