package com.example.learningmanager.fragments.notesmanager.di
import com.example.learningmanager.base.database.AppDatabase
import com.example.learningmanager.fragments.myinspiration.data.local.MyInspirationDataDao
import com.example.learningmanager.fragments.notesmanager.data.local.NoteDataDao
import com.example.learningmanager.fragments.setgoals.data.local.SetGoalsDataDao
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
// TODO change to public module in /di not in fragments/di and change name
abstract class NotesManagerModule {
    companion object {
        @Provides
        fun provideNoteRoom(appDatabase: AppDatabase): NoteDataDao {
            return appDatabase.noteDataDao()
        }
        @Provides
        fun provideGoalsRoom(appDatabase: AppDatabase): SetGoalsDataDao {
            return appDatabase.setGoalsDataDao()
        }
        @Provides
        fun provideMyInspirationRoom(appDatabase: AppDatabase): MyInspirationDataDao {
            return appDatabase.myInspirationDataDao()
        }
    }

}