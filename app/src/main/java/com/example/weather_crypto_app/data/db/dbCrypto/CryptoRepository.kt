package com.example.weather_crypto_app.data.db.dbCrypto

import androidx.lifecycle.LiveData

class CryptoRepository(private val dbDao: DbDao) {
    val readAllData: LiveData<List<DbCrypto>> = dbDao.getAll()

    fun addCoins(dbCrypto: DbCrypto) {
        dbDao.insertCoin(dbCrypto)
    }

    fun deleteCoin(dbCrypto: DbCrypto) {
        dbDao.delete(dbCrypto)
    }
}