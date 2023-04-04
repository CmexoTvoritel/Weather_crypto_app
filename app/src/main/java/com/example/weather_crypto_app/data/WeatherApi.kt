package com.example.weather_crypto_app.data

import com.example.weather_crypto_app.models.weather.info.weatherInfo
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WeatherApi {
    @GET("data/2.5/weather?lat={lat}&lon={lon}&appid=")
    suspend fun getWeather(@Query("lat") lat: String, @Query("lon") lon: String, @Query("appid") key: String): weatherInfo
}