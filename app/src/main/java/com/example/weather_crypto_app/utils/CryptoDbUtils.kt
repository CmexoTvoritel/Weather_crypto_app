package com.example.weather_crypto_app.utils

import com.example.weather_crypto_app.data.viewmodel.CryptoViewModel
import com.example.weather_crypto_app.data.model.DbCrypto
import com.example.weather_crypto_app.presentation.model.CryptoAddModel

class CryptoDbUtils(private val cryptoViewModel: CryptoViewModel) {


    private fun insertDataToCoinDatabase(data: CryptoAddModel) {
        val name = data.nameCoin
        val cost = data.cost
        val image = data.image
        val changeCost = data.price_change
        val coinData = DbCrypto(0, name, image, cost, changeCost)
        cryptoViewModel.addCoins(coinData)
    }

    private fun deleteDataOfCoinDatabase(data: CryptoAddModel) {
        val name = data.nameCoin
        val image = data.image
        val cost = data.cost
        val changeCost = data.price_change
        val coinData = DbCrypto(data.uid, name, image, cost, changeCost)
        cryptoViewModel.deleteCoins(coinData)
    }

    fun publicInsertData(data: CryptoAddModel) {
        insertDataToCoinDatabase(data)
    }

    fun publicDeleteData(data: CryptoAddModel) {
        deleteDataOfCoinDatabase(data)
    }

}