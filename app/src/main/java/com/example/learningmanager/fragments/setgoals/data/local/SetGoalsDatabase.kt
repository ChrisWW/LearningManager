package com.example.learningmanager.fragments.setgoals.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.learningmanager.fragments.setgoals.data.SetGoalsData

@Database(entities = [SetGoalsData::class], version = 5)
abstract class SetGoalsDatabase : RoomDatabase() {

    abstract fun setGoalsDataDao(): SetGoalsDataDao

    companion object {
        @Volatile
        private var INSTANCE: SetGoalsDatabase? = null

        fun getDatabase(context: Context): SetGoalsDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SetGoalsDatabase::class.java,
                    "setgoals_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}