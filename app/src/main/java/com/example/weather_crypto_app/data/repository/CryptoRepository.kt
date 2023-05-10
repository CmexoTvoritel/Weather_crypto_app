package com.example.weather_crypto_app.data.repository

import androidx.lifecycle.LiveData
import com.example.weather_crypto_app.data.dao.DbDao
import com.example.weather_crypto_app.data.model.DbCrypto

class CryptoRepository(private val dbDao: DbDao) {
    val readAllData: LiveData<List<DbCrypto>> = dbDao.getAll()

    fun addCoins(dbCrypto: DbCrypto) {
        dbDao.insertCoin(dbCrypto)
    }

    fun deleteCoin(dbCrypto: DbCrypto) {
        dbDao.delete(dbCrypto)
    }
}