package com.example.weather_crypto_app.data.db.dbCrypto

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DbCrypto::class], version = 1, exportSchema = false)
abstract class CryptoDataBase: RoomDatabase() {
    abstract fun dbDao(): DbDao

    companion object{
        @Volatile
        private var INSTANCE: CryptoDataBase? = null

        fun getDatabase(context: Context): CryptoDataBase {
            val tempInstance = INSTANCE
            if(tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CryptoDataBase::class.java,
                    "crypto_table"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}