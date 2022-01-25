package com.example.learningmanager.fragments.inspirationquotes.di

import com.example.learningmanager.fragments.inspirationquotes.data.remote.InspirationQuotesApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
abstract class InspirationQuotesModule {

    companion object {

        @Provides
        fun provideInspirationQuotesApi(retrofit: Retrofit) : InspirationQuotesApi =
            retrofit.create(InspirationQuotesApi::class.java)
    }
}