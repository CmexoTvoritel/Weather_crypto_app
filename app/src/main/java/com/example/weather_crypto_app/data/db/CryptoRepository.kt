package com.example.weather_crypto_app.data.db

import androidx.lifecycle.LiveData

class CryptoRepository(private val dbDao: DbDao) {
    val readAllData: LiveData<List<DbCrypto>> = dbDao.getAll()

    suspend fun addCoins(dbCrypto: DbCrypto) {
        dbDao.insertCoin(dbCrypto)
    }

    suspend fun deleteCoin(dbCrypto: DbCrypto) {
        dbDao.delete(dbCrypto)
    }
}