package com.example.learningmanager.fragments.myinspiration.di

import com.example.learningmanager.fragments.myinspiration.FirebaseManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class MyInspirationModule {

    companion object {
        @Provides
        fun provideInspirationFirebaseStore(firebaseFirestore: FirebaseFirestore, firebaseFirebaseStorage: FirebaseStorage, firebaseAuth: FirebaseAuth) : FirebaseManager =
            FirebaseManager(firebaseFirestore, firebaseFirebaseStorage, firebaseAuth)
    }
}