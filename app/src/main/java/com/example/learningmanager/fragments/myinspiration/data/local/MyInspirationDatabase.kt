package com.example.learningmanager.fragments.myinspiration.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.learningmanager.fragments.myinspiration.data.MyInspirationData
import com.example.learningmanager.fragments.notesmanager.data.NoteData
import com.example.learningmanager.fragments.notesmanager.data.local.NoteDataDao

@Database(entities = [MyInspirationData::class], version = 1)
abstract class MyInspirationDatabase : RoomDatabase() {

    abstract fun myInspirationDataDao(): MyInspirationDataDao

    companion object {
        @Volatile
        private var INSTANCE: MyInspirationDatabase? = null

        fun getDatabase(context: Context): MyInspirationDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MyInspirationDatabase::class.java,
                    "myinspiration_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
