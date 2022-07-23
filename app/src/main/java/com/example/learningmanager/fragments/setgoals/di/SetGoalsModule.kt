package com.example.learningmanager.fragments.setgoals.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class SetGoalsModule {

//    companion object {
//        @Provides
//        fun provideInspirationFirebaseStore(firebaseFirestore: FirebaseFirestore, firebaseFirebaseStorage: FirebaseStorage, firebaseAuth: FirebaseAuth) : FirebaseManager =
//            FirebaseManager(firebaseFirestore, firebaseFirebaseStorage, firebaseAuth)
//    }
}
