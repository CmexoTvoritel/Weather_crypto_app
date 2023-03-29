package com.example.weather_crypto_app.data.names.city

import com.example.weather_crypto_app.models.CityWeatherModel

class CityNamesWeather {
    val cityNames: MutableList<CityWeatherModel> = mutableListOf(
        CityWeatherModel("RU", "Москва", "Moscow"),
        CityWeatherModel("RU", "Ростов на Дону", "Rostov"),
        CityWeatherModel("RU", "Таганрог", "Taganrog"),
        CityWeatherModel("GB", "Лондон", "London"),
        CityWeatherModel("JP", "Токио", "Tokyo"),
        CityWeatherModel("RU", "Владивосток", "Vladivostok"),
        CityWeatherModel("FR", "Париж", "Paris")
    )
}