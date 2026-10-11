package com.aiassistant.app

import android.app.Application
import androidx.room.Room
import com.aiassistant.app.data.local.AppDatabase

class MainApplication : Application() {

    lateinit var database: AppDatabase
        private set

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "assistant_database"
        ).fallbackToDestructiveMigration().build()
    }
}
