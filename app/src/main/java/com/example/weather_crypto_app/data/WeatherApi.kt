package com.example.weather_crypto_app.data

import com.example.weather_crypto_app.models.weather.info.weatherInfo
import retrofit2.http.GET
import retrofit2.http.Path

interface WeatherApi {
    @GET("data/2.5/weather?lat={lat}&lon={lon}&appid=22c2b837bf6f65a956144d42d02343bb")
    suspend fun getWeather(@Path("lat") lat: Double, @Path("lon") lon: Double): weatherInfo
}