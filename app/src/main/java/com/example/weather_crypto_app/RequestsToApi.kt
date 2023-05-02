package com.example.weather_crypto_app

import com.example.weather_crypto_app.data.CordsWeatherApi
import com.example.weather_crypto_app.data.CryptoApi
import com.example.weather_crypto_app.data.WeatherApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RequestsToApi {

    private fun generateRequestToApiCoins(): CryptoApi {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.coingecko.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(CryptoApi::class.java)
    }

    private fun generateRequestToApiCords(): CordsWeatherApi {
        val retrofitCords = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofitCords.create(CordsWeatherApi::class.java)
    }

    private fun generateRequestToApiWeather(): WeatherApi {
        val retrofitInfo = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofitInfo.create(WeatherApi::class.java)
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