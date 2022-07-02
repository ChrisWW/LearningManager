package com.example.learningmanager.fragments.myinspiration.domain

import android.content.Context
import android.net.Uri
import androidx.fragment.app.FragmentActivity
import com.example.learningmanager.fragments.myinspiration.FirebaseManager
import com.example.learningmanager.fragments.myinspiration.data.MyInspirationDetailsResponse
import javax.inject.Inject

class AddImageMyInspirationFirebaseStorageUseCase @Inject constructor(private val firebaseManager: FirebaseManager) {
    suspend fun create(imgUrl: Uri, activity: FragmentActivity) {
        firebaseManager.uploadImage(imgUrl, activity)
    }
}