package com.example.weather_crypto_app.data.db.dbMap

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
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

    fun deleteCity(dbMap: DbMap) {
        viewModelScope.launch(Dispatchers.IO) {
            mapRepository.deleteCity(dbMap)
        }
    }
}