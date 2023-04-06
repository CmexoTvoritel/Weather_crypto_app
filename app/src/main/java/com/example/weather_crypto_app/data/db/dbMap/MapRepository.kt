package com.example.weather_crypto_app.data.db.dbMap

import androidx.lifecycle.LiveData

class MapRepository(private val dbMapDao: DbMapDao) {
    val readAllData: LiveData<List<DbMap>> = dbMapDao.getAll()

    suspend fun addCity(dbMap: DbMap) {
        dbMapDao.insertMap(dbMap)
    }

    suspend fun deleteCity(dbMap: DbMap) {
        dbMapDao.deleteMap(dbMap)
    }
}