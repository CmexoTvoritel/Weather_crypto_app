package com.example.weather_crypto_app.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.weather_crypto_app.data.database.MenuDataBase
import com.example.weather_crypto_app.data.model.DbMenu
import com.example.weather_crypto_app.data.repository.MenuRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MenuViewModel(application: Application): AndroidViewModel(application) {
    val readAllData: LiveData<List<DbMenu>>
    private val menuRepository: MenuRepository

    init {
        val dbMenuDao = MenuDataBase.getDatabase(application).dbMenuDao()
        menuRepository = MenuRepository(dbMenuDao)
        readAllData = menuRepository.readAllData
    }

    fun addMenu(dbMenu: DbMenu) {
        viewModelScope.launch(Dispatchers.IO) {
            menuRepository.addMenu(dbMenu)
        }
    }

    fun updateMenu(dbMenu: DbMenu) {
        viewModelScope.launch(Dispatchers.IO) {
            menuRepository.updateMenu(dbMenu)
        }
    }

    fun deleteMenu(dbMenu: DbMenu) {
        viewModelScope.launch(Dispatchers.IO) {
            menuRepository.deleteMenu(dbMenu)
        }
    }

}