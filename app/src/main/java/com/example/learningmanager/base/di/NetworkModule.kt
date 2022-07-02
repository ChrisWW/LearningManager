package com.example.learningmanager.base.di

import com.example.learningmanager.BuildConfig
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideFirestore() = FirebaseFirestore.getInstance()

    @Provides
    @Singleton
    fun provideFirebaseStorage() = FirebaseStorage.getInstance()

//    @Provides
//    @Singleton
//    fun provideStorage() = FirebaseFirestore.getInstance()

    @Provides
    fun provideGson() = Gson()

    @Provides
    fun provideGsonConverterFactory(gson: Gson): GsonConverterFactory =
        GsonConverterFactory.create(gson)

    @Provides
    fun provideRetrofit(factory: GsonConverterFactory) : Retrofit =
        Retrofit.Builder()
            .addConverterFactory(factory)
//            .client(client)
            .baseUrl(BuildConfig.BASE_URL)
            .build()
}