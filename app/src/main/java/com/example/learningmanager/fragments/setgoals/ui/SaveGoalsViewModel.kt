package com.example.learningmanager.fragments.setgoals.ui

import com.example.learningmanager.base.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SaveGoalsViewModel @Inject constructor() : BaseViewModel() {


    fun onBackNavigate() {
        navigateBack()
    }
}