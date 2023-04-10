package com.example.weather_crypto_app.data.db.dbCrypto

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DbDao {
    @Query("SELECT * FROM crypto_table ORDER BY uid ASC")
    fun getAll(): LiveData<List<DbCrypto>>

    @Insert
    suspend fun insertCoin(vararg dbCrypto: DbCrypto)

    @Delete
    suspend fun delete(dbCrypto: DbCrypto)
}