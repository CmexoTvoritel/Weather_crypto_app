package com.example.weather_crypto_app.data.db.dbMenu

import androidx.lifecycle.LiveData
import androidx.room.*

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