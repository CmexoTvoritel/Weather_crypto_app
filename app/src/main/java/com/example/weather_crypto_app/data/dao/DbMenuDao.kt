package com.example.weather_crypto_app.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.weather_crypto_app.data.model.DbMenu

@Dao
interface DbMenuDao {
    @Query("SELECT * FROM menu_table ORDER BY uid ASC")
    fun getAll(): LiveData<List<DbMenu>>

    @Insert
    fun insertMenu(dbMenu: DbMenu)

    @Update
    fun updateMenu(dbMenu: DbMenu)

    @Delete
    fun deleteMenu(dbMenu: DbMenu)
}