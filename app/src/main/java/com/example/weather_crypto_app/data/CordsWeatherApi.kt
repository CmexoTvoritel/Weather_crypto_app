package com.example.weather_crypto_app.data

import com.example.weather_crypto_app.models.weather.coords.CityCoordsItem
import retrofit2.http.GET
import retrofit2.http.Query

interface CordsWeatherApi {
    @GET("geo/1.0/direct")
    suspend fun getCordsWeather(@Query("q") city: String?,
                                @Query("limit") limit: Int = 5,
                                @Query("appid") appid: String = "22c2b837bf6f65a956144d42d02343bb")
    : List<CityCoordsItem>
}