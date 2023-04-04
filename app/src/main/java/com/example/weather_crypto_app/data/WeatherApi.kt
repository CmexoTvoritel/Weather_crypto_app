package com.example.weather_crypto_app.data

import com.example.weather_crypto_app.models.weather.info.weatherInfo
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WeatherApi {
    @GET("data/2.5/weather?lat=45.07&lon=37.6&appid=22c2b837bf6f65a956144d42d02343bb")
    suspend fun getWeather(@Query("lat") lat: Double, @Query("lon") lon: Double): weatherInfo
}