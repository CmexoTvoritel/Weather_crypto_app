package com.example.weather_crypto_app.data.db.dbMenu

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface DbMenuDao {
    @Query("SELECT * FROM menu_table ORDER BY uid ASC")
    fun getAll(): LiveData<List<DbMenu>>

    @Insert
    suspend fun insertMenu(dbMenu: DbMenu)

    @Update
    suspend fun updateMenu(dbMenu: DbMenu)

    @Delete
    suspend fun deleteMenu(dbMenu: DbMenu)
}