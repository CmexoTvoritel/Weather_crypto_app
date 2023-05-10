package com.example.weather_crypto_app.data.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.weather_crypto_app.data.database.MapDataBase
import com.example.weather_crypto_app.data.model.DbMap
import com.example.weather_crypto_app.data.repository.MapRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MapViewModel(application: Application): AndroidViewModel(application) {
    val readAllData: LiveData<List<DbMap>>
    private val mapRepository: MapRepository

    init {
        val dbMapDao = MapDataBase.getDatabase(application).dbMapDao()
        mapRepository = MapRepository(dbMapDao)
        readAllData = mapRepository.readAllData
    }

    fun addCity(dbMap: DbMap) {
        viewModelScope.launch(Dispatchers.IO) {
            mapRepository.addCity(dbMap)
        }
    }

    fun updateMap(dbMap: DbMap) {
        viewModelScope.launch(Dispatchers.IO) {
            mapRepository.updateMap(dbMap)
        }
    }

    fun deleteCity(dbMap: DbMap) {
        viewModelScope.launch(Dispatchers.IO) {
            mapRepository.deleteCity(dbMap)
        }
    }
}