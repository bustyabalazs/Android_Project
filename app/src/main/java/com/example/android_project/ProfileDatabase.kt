package com.example.android_project

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import java.security.AccessControlContext

@Database(entities = [Profile::class], version = 1, exportSchema = false)
abstract class ProfileDatabase : RoomDatabase() {
    abstract fun profileDao(): ProfileDAO

    companion object {
        @Volatile //rights to this field are immediately visible for other threads
        private var Instance: ProfileDatabase? = null

        fun getDatabase(context: Context): ProfileDatabase {
            val tempInstance = Instance
            //ha letezik visszateritjuk
            if (tempInstance != null) {
                return tempInstance
            }
            //maskepp letrehozzuk
            synchronized(this) {} //will be protected from concurrent execution by multiple threads
            val instance = Room.databaseBuilder(
                context.applicationContext,
                ProfileDatabase::class.java,
                "profile_database"
            ).build()
            Instance = instance
            return instance
        }
    }
}