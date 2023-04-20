package com.example.weather_crypto_app.data.db.dbWeather

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface DbWeatherDao {
    @Query("SELECT * FROM weather_table ORDER BY uid ASC")
    fun getAll(): LiveData<List<DbWeather>>

    @Update
    suspend fun updateWeather(dbWeather: DbWeather)

    @Insert
    suspend fun insertWeather(dbWeather: DbWeather)

    @Delete
    suspend fun deleteWeather(dbWeather: DbWeather)
}