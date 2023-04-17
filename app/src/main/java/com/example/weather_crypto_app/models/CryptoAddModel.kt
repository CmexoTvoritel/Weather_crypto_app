package com.example.weather_crypto_app.models

data class CryptoAddModel (
        var uid: Int,
        val image: String,
        val nameCoin: String,
        val cost: Double,
        var enableCoin: Boolean,
)