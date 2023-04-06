package com.example.weather_crypto_app.models

data class MainMenuModel (
    val nameMenu: String?,
    val nameButton: String,
    val status: Boolean,
    val type: MainMenuModules
)

enum class MainMenuModules {
    MAP,
    WEATHER,
    COINS
}