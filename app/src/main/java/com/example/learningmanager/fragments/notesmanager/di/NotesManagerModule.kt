package com.example.learningmanager.fragments.notesmanager.di
import com.example.learningmanager.base.database.AppDatabase
import com.example.learningmanager.fragments.notesmanager.data.local.NoteDataDao
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class NotesManagerModule {
    companion object {
        @Provides
        fun provideNotificationRoom(appDatabase: AppDatabase): NoteDataDao {
            return appDatabase.noteDataDao()
        }
    }

}