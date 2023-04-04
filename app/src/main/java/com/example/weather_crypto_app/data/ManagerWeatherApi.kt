package com.example.weather_crypto_app.data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ManagerWeatherApi {
    fun getDataWeather(lat: Double, lon: Double) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val weatherApi = retrofit.create(WeatherApi::class.java)
        CoroutineScope(Dispatchers.IO).launch {
            val weather = weatherApi.getWeather(lat.toString(), lon.toString(), "22c2b837bf6f65a956144d42d02343bb")
        }
    }
}