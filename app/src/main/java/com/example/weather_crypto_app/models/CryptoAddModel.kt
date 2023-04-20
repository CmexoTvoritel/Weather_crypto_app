package com.example.weather_crypto_app.models

data class CryptoAddModel (
        var uid: Int,
        val image: String,
        val nameCoin: String,
        val cost: Double,
        val price_change: Double,
        var enableCoin: Boolean,
)