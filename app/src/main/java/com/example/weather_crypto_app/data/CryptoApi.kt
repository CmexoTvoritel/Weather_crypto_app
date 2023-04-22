package com.example.weather_crypto_app.data

import com.example.weather_crypto_app.models.crypto.cryptoRepItem
import retrofit2.http.GET

interface CryptoApi {
    @GET("api/v3/coins/markets?vs_currency=usd&order=market_cap_desc&")
    suspend fun getCrypto(): List<cryptoRepItem>
}