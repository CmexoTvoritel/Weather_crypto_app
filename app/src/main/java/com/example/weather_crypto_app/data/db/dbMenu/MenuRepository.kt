package com.example.weather_crypto_app.data.db.dbMenu

import androidx.lifecycle.LiveData

class MenuRepository(private val dbMenuDao: DbMenuDao) {
    val readAllData: LiveData<List<DbMenu>> = dbMenuDao.getAll()

    fun addMenu(dbMenu: DbMenu) {
        dbMenuDao.insertMenu(dbMenu)
    }

    fun updateMenu(dbMenu: DbMenu) {
        dbMenuDao.updateMenu(dbMenu)
    }

    fun deleteMenu(dbMenu: DbMenu) {
        dbMenuDao.deleteMenu(dbMenu)
    }
}