package com.example.weather_crypto_app.data.db.dbWeather

import androidx.lifecycle.LiveData

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