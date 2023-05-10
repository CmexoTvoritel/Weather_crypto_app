package com.example.weather_crypto_app.network.services

import com.example.weather_crypto_app.network.model.crypto.CryptoRepItem
import retrofit2.http.GET

interface CryptoService {
    @GET("api/v3/coins/markets?vs_currency=usd&order=market_cap_desc&")
    suspend fun getCrypto(): List<CryptoRepItem>
}