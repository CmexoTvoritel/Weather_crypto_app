package com.example.weather_crypto_app.models

import com.example.weather_crypto_app.data.db.dbCrypto.DbCrypto

data class MainMenuModel (
    val nameMenu: String?,
    val nameButton: String,
    val status: Boolean,
    val type: MainMenuModules,
    val cryptoList: List<DbCrypto>
)

enum class MainMenuModules {
    MAP,
    WEATHER,
    COINS
}