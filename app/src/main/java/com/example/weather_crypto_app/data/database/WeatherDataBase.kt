package com.example.weather_crypto_app.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.weather_crypto_app.data.dao.DbWeatherDao
import com.example.weather_crypto_app.data.model.DbWeather

@Database(entities = [DbWeather::class], version = 1, exportSchema = false)
abstract class WeatherDataBase: RoomDatabase() {

    abstract fun dbWeatherDao(): DbWeatherDao

    companion object {
        @Volatile
        private var INSTANCE: WeatherDataBase? = null

        fun getDatabase(context: Context): WeatherDataBase {
            val tempInstance = INSTANCE
            if(tempInstance != null) return tempInstance
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WeatherDataBase::class.java,
                    "weather_table"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}