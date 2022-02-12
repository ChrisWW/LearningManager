package com.example.learningmanager.base.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.learningmanager.fragments.notesmanager.data.NoteData
import com.example.learningmanager.fragments.notesmanager.data.local.NoteDataDao

@Database(entities = [NoteData::class], version = 3)
abstract class AppDatabase : RoomDatabase() {
    abstract fun noteDataDao(): NoteDataDao

}