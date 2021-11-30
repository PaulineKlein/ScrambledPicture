package com.pklein.scrambledpicture

import android.app.Application
import androidx.room.Room
import com.pklein.scrambledpicture.data.AppDatabase

class ScrambledPictureApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        database = Room.databaseBuilder(this, AppDatabase::class.java, "scrambled_picture")
            .build()
    }

    override fun onTerminate() {
        database.close()
        super.onTerminate()
    }

    companion object {
        lateinit var instance: ScrambledPictureApplication
        lateinit var database: AppDatabase
    }
}