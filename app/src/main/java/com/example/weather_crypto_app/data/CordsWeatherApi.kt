package com.example.weather_crypto_app.data

import com.example.weather_crypto_app.models.weather.coords.cityCoords
import retrofit2.http.GET
import retrofit2.http.Path

interface CordsWeatherApi {
    @GET("geo/1.0/direct?q={city}&limit=5&appid=22c2b837bf6f65a956144d42d02343bb")
    suspend fun getCordsWeather(@Path("city") city: String): cityCoords
}