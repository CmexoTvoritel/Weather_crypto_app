package com.example.weather_crypto_app.data.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.weather_crypto_app.data.database.CryptoDataBase
import com.example.weather_crypto_app.data.model.DbCrypto
import com.example.weather_crypto_app.data.repository.CryptoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CryptoViewModel(application: Application): AndroidViewModel(application) {

    val readAllData: LiveData<List<DbCrypto>>
    private val cryptoRepository: CryptoRepository

    init {
        val dbDao = CryptoDataBase.getDatabase(application).dbDao()
        cryptoRepository = CryptoRepository(dbDao)
        readAllData = cryptoRepository.readAllData
    }

    fun addCoins(dbCrypto: DbCrypto) {
        viewModelScope.launch(Dispatchers.IO) {
            cryptoRepository.addCoins(dbCrypto)
        }
    }

    fun deleteCoins(dbCrypto: DbCrypto) {
        viewModelScope.launch(Dispatchers.IO) {
            cryptoRepository.deleteCoin(dbCrypto)
        }
    }
}