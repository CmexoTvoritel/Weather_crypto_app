package com.example.weather_crypto_app.data.repository

import androidx.lifecycle.LiveData
import com.example.weather_crypto_app.data.dao.DbWeatherDao
import com.example.weather_crypto_app.data.model.DbWeather

class WeatherRepository(private val dbWeatherDao: DbWeatherDao) {
    val readAllData: LiveData<List<DbWeather>> = dbWeatherDao.getAll()

    suspend fun addCity(dbWeather: DbWeather) {
        dbWeatherDao.insertWeather(dbWeather)
    }

    suspend fun updateCity(dbWeather: DbWeather) {
        dbWeatherDao.updateWeather(dbWeather)
    }

    suspend fun deleteCity(dbWeather: DbWeather) {
        dbWeatherDao.deleteWeather(dbWeather)
    }

}