package com.example.learningmanager.base.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.learningmanager.fragments.notesmanager.data.NoteData
import com.example.learningmanager.fragments.notesmanager.data.local.NoteDataDao
import com.example.learningmanager.fragments.setgoals.data.SetGoalsData
import com.example.learningmanager.fragments.setgoals.data.local.SetGoalsDataDao

@Database(
    entities = [NoteData::class, SetGoalsData::class], version = 3
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun noteDataDao(): NoteDataDao
    abstract fun setGoalsDataDao(): SetGoalsDataDao

}