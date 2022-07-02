package com.example.learningmanager.fragments.myinspiration.ui

import android.util.Log
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.viewModelScope
import com.example.learningmanager.MainActivity
import com.example.learningmanager.base.ui.BaseViewModel
import com.example.learningmanager.fragments.myinspiration.data.MyInspirationData
import com.example.learningmanager.fragments.myinspiration.data.MyInspirationDetailsResponse
import com.example.learningmanager.fragments.myinspiration.domain.AddMyInspirationItemsUseCase
import com.example.learningmanager.fragments.myinspiration.domain.GetMyInspirationItemsUseCase
import com.github.dhaval2404.imagepicker.ImagePicker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyInspirationViewModel @Inject constructor(
    private val getMyInspirationItemsUseCase: GetMyInspirationItemsUseCase,
    private val addMyInspirationItemsUseCase: AddMyInspirationItemsUseCase
) : BaseViewModel() {

    val myInspirationDataList = MutableStateFlow<List<MyInspirationData>>(emptyList())

    fun getActualState() {
        viewModelScope.launch {
            getMyInspirationItemsUseCase.build(Unit).collect {
                myInspirationDataList.value = it
                Log.d("newNoteData", "${myInspirationDataList.value}")
            }
        }
    }

    fun saveInspiration(newMyInspiration: MyInspirationDetailsResponse) {
        viewModelScope.launch {
            addMyInspirationItemsUseCase.create(newMyInspiration)
            getActualState()
            Log.d("newNoteData", "$newMyInspiration")
        }
    }

    fun acceptPicture() {
//        FirebaseManager().uploadImage(requireContext(), imgURI!!)
    }

    fun addPicture(activity: FragmentActivity) {
        ImagePicker.with(activity)
            .crop()
            .compress(1024)
            .maxResultSize(1080, 1080)
            .start()
    }
}