package com.example.weather_crypto_app.presentation.model

import com.example.weather_crypto_app.data.model.DbCrypto
import com.example.weather_crypto_app.data.model.city.PointCity

data class MainMenuModel (
    val nameMenu: String?,
    val nameButton: String,
    val status: Boolean,
    val type: MainMenuModules,
    val cryptoList: List<DbCrypto>,
    val weatherList: WeatherMenuModel,
    val needPoint: PointCity,
)

enum class MainMenuModules {
    MAP,
    WEATHER,
    COINS,
    ERROR1,
    ERROR2,
}