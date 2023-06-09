package com.example.weather_crypto_app.data.repository

import androidx.lifecycle.LiveData
import com.example.weather_crypto_app.data.dao.DbMapDao
import com.example.weather_crypto_app.data.model.DbMap

class MapRepository(private val dbMapDao: DbMapDao) {
    val readAllData: LiveData<List<DbMap>> = dbMapDao.getAll()

    suspend fun addCity(dbMap: DbMap) {
        dbMapDao.insertMap(dbMap)
    }

    suspend fun updateMap(dbMap: DbMap) {
        dbMapDao.updateMap(dbMap)
    }

    suspend fun deleteCity(dbMap: DbMap) {
        dbMapDao.deleteMap(dbMap)
    }
}