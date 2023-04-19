package com.example.weather_crypto_app.data.names.city

import com.example.weather_crypto_app.models.CityMapModel

class CityNamesMap {
    val cityNames: MutableList<CityMapModel> = mutableListOf(
        CityMapModel("RU", "Москва", "Moscow", PointCity(55.751574, 37.573856)),
        CityMapModel("RU", "Ростов на Дону", "Rostov", PointCity(47.235254, 39.713820)),
        CityMapModel("RU", "Таганрог", "Taganrog", PointCity(47.216690, 38.912160)),
        CityMapModel("GB", "Лондон", "London", PointCity(51.512006876579996, -0.12575237796959887)),
        CityMapModel("JP", "Токио", "Tokyo", PointCity(35.697794277314344, 139.77408981148892)),
        CityMapModel("RU", "Владивосток", "Vladivostok", PointCity(43.12873403067113, 131.90985319436243)),
        CityMapModel("FR", "Париж", "Paris", PointCity(48.853065282108396, 2.3439425275931196))
    )
}