package com.example.weather_crypto_app.network.services.weather

import com.example.weather_crypto_app.network.model.weather.info.WeatherInfo
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET("data/2.5/weather")
    suspend fun getWeather(@Query("lat") lat: String, @Query("lon") lon: String,
                           @Query("appid") key: String = "22c2b837bf6f65a956144d42d02343bb",
                           @Query("lang") laang: String = "ru",
                           @Query("units") units: String = "metric"): WeatherInfo
}