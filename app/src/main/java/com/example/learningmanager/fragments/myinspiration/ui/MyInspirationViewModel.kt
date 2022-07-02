package com.example.learningmanager.fragments.myinspiration.ui

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.viewModelScope
import com.example.learningmanager.MainActivity
import com.example.learningmanager.base.ui.BaseViewModel
import com.example.learningmanager.fragments.myinspiration.data.MyInspirationData
import com.example.learningmanager.fragments.myinspiration.data.MyInspirationDetailsResponse
import com.example.learningmanager.fragments.myinspiration.domain.AddImageMyInspirationFirebaseStorageUseCase
import com.example.learningmanager.fragments.myinspiration.domain.AddMyInspirationItemsFirebaseUseCase
import com.example.learningmanager.fragments.myinspiration.domain.AddMyInspirationItemsUseCase
import com.example.learningmanager.fragments.myinspiration.domain.GetMyInspirationItemsUseCase
import com.github.dhaval2404.imagepicker.ImagePicker
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyInspirationViewModel @Inject constructor(
    @ApplicationContext appContext: Context,
    private val getMyInspirationItemsUseCase: GetMyInspirationItemsUseCase,
    private val addMyInspirationItemsUseCase: AddMyInspirationItemsUseCase,
    private val addMyInspirationItemsFirebaseUseCase: AddMyInspirationItemsFirebaseUseCase,
    private val addImageMyInspirationFirebaseStorageUseCase: AddImageMyInspirationFirebaseStorageUseCase
) : BaseViewModel() {
    val context = appContext
    val myInspirationDataList = MutableStateFlow<List<MyInspirationData>>(emptyList())

    fun getActualState() {
        viewModelScope.launch {
            getMyInspirationItemsUseCase.build(Unit).collect {
                myInspirationDataList.value = it
                Log.d("newNoteData", "${myInspirationDataList.value}")
            }
        }
    }

    fun saveInspiration(newMyInspiration: MyInspirationDetailsResponse, activity: FragmentActivity) {
        viewModelScope.launch {
            addMyInspirationItemsUseCase.create(newMyInspiration)
            getActualState()
            addMyInspirationItemsFirebaseUseCase.create(newMyInspiration, activity)
            Log.d("newNoteData", "$newMyInspiration")
            navigateBack()
        }
    }

    suspend fun acceptPicture(imgUrl: Uri, activity: FragmentActivity) {
//        FirebaseManager().uploadImage(requireContext(), imgURI!!)
        addImageMyInspirationFirebaseStorageUseCase.create(imgUrl, activity)
    }

    fun addPicture(activity: FragmentActivity) {
        ImagePicker.with(activity)
            .crop()
            .compress(1024)
            .maxResultSize(1080, 1080)
            .start()
    }
}