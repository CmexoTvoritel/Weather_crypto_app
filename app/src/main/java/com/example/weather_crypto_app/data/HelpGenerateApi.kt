package com.example.weather_crypto_app.data

import com.example.weather_crypto_app.models.weather.coords.cityCoordsItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HelpGenerateApi {
    fun helpGenerateApi(city: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val cordsApi = retrofit.create(CordsWeatherApi::class.java)
        CoroutineScope(Dispatchers.IO).launch {
            val cords = cordsApi.getCordsWeather(city)
            val getlatloncords = getLatLonCords(cords[0])
            val managerWeatherApi = ManagerWeatherApi()
            val lat = getlatloncords.latGenerate()
            val lon = getlatloncords.lonGenerate()
            managerWeatherApi.getDataWeather(lat, lon)
        }
    }


}

private class getLatLonCords(cords: cityCoordsItem) {

    private val need_route = cords

    fun latGenerate(): Double {
        var answer: Double = 0.00
        answer = need_route.lat
        return answer
    }

    fun lonGenerate(): Double {
        var answer: Double = 0.00
        answer = need_route.lon
        return answer
    }
}