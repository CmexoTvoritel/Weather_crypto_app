package com.example.weather_crypto_app.data.db.dbWeather

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WeatherViewModel(application: Application): AndroidViewModel(application) {
    val readAllData: LiveData<List<DbWeather>>
    private val weatherRepository: WeatherRepository

    init {
        val dbWeatherDao = WeatherDataBase.getDatabase(application).dbWeatherDao()
        weatherRepository = WeatherRepository(dbWeatherDao)
        readAllData = weatherRepository.readAllData
    }

    fun addCity(dbWeather: DbWeather) {
        viewModelScope.launch(Dispatchers.IO) {
            weatherRepository.addCity(dbWeather)
        }
    }

    fun updateCity(dbWeather: DbWeather) {
        viewModelScope.launch(Dispatchers.IO) {
            weatherRepository.updateCity(dbWeather)
        }
    }

    fun deleteCity(dbWeather: DbWeather) {
        viewModelScope.launch(Dispatchers.IO) {
            weatherRepository.deleteCity(dbWeather)
        }
    }
}