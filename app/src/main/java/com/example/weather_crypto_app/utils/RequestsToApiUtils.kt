package com.example.weather_crypto_app.utils

import com.example.weather_crypto_app.network.services.weather.CordsWeatherService
import com.example.weather_crypto_app.network.services.CryptoService
import com.example.weather_crypto_app.network.services.weather.WeatherService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RequestsToApiUtils {

    private fun generateRequestToApiCoins(): CryptoService {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.coingecko.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(CryptoService::class.java)
    }

    private fun generateRequestToApiCords(): CordsWeatherService {
        val retrofitCords = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofitCords.create(CordsWeatherService::class.java)
    }

    private fun generateRequestToApiWeather(): WeatherService {
        val retrofitInfo = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofitInfo.create(WeatherService::class.java)
    }


    fun publicGenerateRequest(key: String): Any {
        when(key) {
            "Coin" -> {
                return generateRequestToApiCoins()
            }
            "Cords" -> {
                return generateRequestToApiCords()
            }
            "Weather" -> {
                return generateRequestToApiWeather()
            }
        }
        return false
    }

}