package com.example.weather_crypto_app.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.weather_crypto_app.data.dao.DbMenuDao
import com.example.weather_crypto_app.data.model.DbMenu

@Database(entities = [DbMenu::class], version = 1, exportSchema = false)
abstract class MenuDataBase: RoomDatabase() {

    abstract fun dbMenuDao(): DbMenuDao

    companion object {
        @Volatile
        private var INSTANCE: MenuDataBase? = null

        fun getDatabase(context: Context): MenuDataBase {
            val tempInstance = INSTANCE
            if(tempInstance != null) return tempInstance
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MenuDataBase::class.java,
                    "menu_table"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}