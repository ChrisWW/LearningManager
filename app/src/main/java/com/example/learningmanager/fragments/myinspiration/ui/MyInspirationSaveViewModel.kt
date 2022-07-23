package com.example.learningmanager.fragments.myinspiration.ui

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.learningmanager.base.ui.BaseViewModel
import com.example.learningmanager.fragments.myinspiration.data.MyInspirationData
import com.example.learningmanager.fragments.myinspiration.data.MyInspirationDetailsResponse
import com.example.learningmanager.fragments.myinspiration.domain.AddMyInspirationItemsUseCase
import com.example.learningmanager.fragments.myinspiration.domain.GetMyInspirationItemsUseCase
import com.example.learningmanager.fragments.notesmanager.data.NoteData
import com.example.learningmanager.fragments.notesmanager.data.NoteDataDetailsResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyInspirationSaveViewModel @Inject constructor(
) : BaseViewModel() {

}