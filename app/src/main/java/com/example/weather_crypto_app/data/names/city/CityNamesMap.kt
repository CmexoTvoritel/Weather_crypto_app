package com.example.weather_crypto_app.data.names.city

import com.example.weather_crypto_app.models.CityMapModel

class CityNamesMap {
    val cityNames: MutableList<CityMapModel> = mutableListOf(
        CityMapModel("RU", "Москва", "Moscow"),
        CityMapModel("RU", "Ростов на Дону", "Rostov"),
        CityMapModel("RU", "Таганрог", "Taganrog"),
        CityMapModel("GB", "Лондон", "London"),
        CityMapModel("JP", "Токио", "Tokyo"),
        CityMapModel("RU", "Владивосток", "Vladivostok"),
        CityMapModel("FR", "Париж", "Paris")
    )
}