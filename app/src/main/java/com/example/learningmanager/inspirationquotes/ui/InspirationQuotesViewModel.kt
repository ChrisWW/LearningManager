package com.example.learningmanager.inspirationquotes.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.learningmanager.base.ui.BaseViewModel
import com.example.learningmanager.inspirationquotes.data.InspirationQuotesDetailsResponse
import com.example.learningmanager.inspirationquotes.domain.GetPictureItemUseCase
import com.example.learningmanager.inspirationquotes.domain.GetRandomPictureItemUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.Optional.empty
import javax.inject.Inject

@HiltViewModel
class InspirationQuotesViewModel @Inject constructor(
    application: Application,
    private val getPictureItemUseCase: GetPictureItemUseCase,
    private val getRandomPictureItemUseCase: GetRandomPictureItemUseCase
) : BaseViewModel() {
    // is it okay with ? and null
    val randomPicture: Flow<InspirationQuotesDetailsResponse> = getRandomPictureItemUseCase.build(Unit)

}