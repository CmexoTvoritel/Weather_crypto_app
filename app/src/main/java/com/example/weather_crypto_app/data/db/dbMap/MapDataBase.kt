package com.example.weather_crypto_app.data.db.dbMap

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.weather_crypto_app.data.db.dbCrypto.DbDao

@Database(entities = [DbMap::class], version = 1, exportSchema = false)
abstract class MapDataBase: RoomDatabase() {
    abstract fun dbMapDao(): DbMapDao

    companion object {
        @Volatile
        private var INSTANCE: MapDataBase? = null

        fun getDatabase(context: Context): MapDataBase {
            val tempInstance = INSTANCE
            if(tempInstance != null) return tempInstance
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MapDataBase::class.java,
                    "map_table"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}