package com.example.learningmanager.base.di

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.example.learningmanager.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

//    @Singleton
//    @Provides
//    fun provideActivityContext() = Application()

    companion object {
//        @Provides
//        @ActivityContext
//        fun provideActivityContext(activity: AppCompatActivity): Context = activity
//
//        @Provides
//        @ActivityContext
//        fun provideActivityContext(@ActivityContext contextApp: Context,): Context = contextApp
//        @ApplicationContext
//        private val context: Context,
    }
}