package com.example.learningmanager.fragments.setgoals.ui

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.example.learningmanager.base.ui.BaseViewModel
import com.example.learningmanager.fragments.notesmanager.domain.AddNotesItemsUseCase
import com.example.learningmanager.fragments.setgoals.data.SetGoalsData
import com.example.learningmanager.fragments.setgoals.data.SetGoalsDataDetailsResponse
import com.example.learningmanager.fragments.setgoals.domain.AddGoalsItemsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SaveGoalsViewModel @Inject constructor(
    private val application: Application,
    private val addGoalsItemsUseCase: AddGoalsItemsUseCase
) : BaseViewModel() {


    fun onBackNavigate() {
        navigateBack()
    }

    fun saveGoal(newSetGoalsData: SetGoalsDataDetailsResponse) {
        viewModelScope.launch {
            addGoalsItemsUseCase.create(newSetGoalsData)
        }
    }

}