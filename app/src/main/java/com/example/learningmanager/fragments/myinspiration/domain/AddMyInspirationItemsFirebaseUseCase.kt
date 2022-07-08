package com.example.learningmanager.fragments.myinspiration.domain

import android.content.Context
import androidx.fragment.app.FragmentActivity
import com.example.learningmanager.fragments.myinspiration.FirebaseManager
import com.example.learningmanager.fragments.myinspiration.data.MyInspirationData
import com.example.learningmanager.fragments.myinspiration.data.MyInspirationDetailsResponse
import com.example.learningmanager.fragments.myinspiration.data.local.MyInspirationDataDao
import dagger.Provides
import javax.inject.Inject

// provides context hilt TODO
class AddMyInspirationItemsFirebaseUseCase @Inject constructor(private val firebaseManager: FirebaseManager) {
    suspend fun create(params: MyInspirationDetailsResponse, activity: FragmentActivity) {
        firebaseManager.saveFireStore(
            params.title,
            params.description,
            params.date,
            params.authorQuote,
            params.quote,
            params.localization,
            activity
        )
    }
}