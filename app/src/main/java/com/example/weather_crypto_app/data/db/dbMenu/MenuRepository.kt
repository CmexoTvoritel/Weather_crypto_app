package com.example.weather_crypto_app.data.db.dbMenu

import androidx.lifecycle.LiveData

class MenuRepository(private val dbMenuDao: DbMenuDao) {
    val readAllData: LiveData<List<DbMenu>> = dbMenuDao.getAll()

    suspend fun addMenu(dbMenu: DbMenu) {
        dbMenuDao.insertMenu(dbMenu)
    }

    suspend fun updateMenu(dbMenu: DbMenu) {
        dbMenuDao.updateMenu(dbMenu)
    }

    suspend fun deleteMenu(dbMenu: DbMenu) {
        dbMenuDao.deleteMenu(dbMenu)
    }
}