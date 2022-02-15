package com.example.learningmanager.fragments.setgoals.ui

import com.example.learningmanager.base.ui.BaseViewModel
import com.example.learningmanager.fragments.setgoals.route.SetGoalsToSaveScreenKey
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SetGoalsViewModel @Inject constructor() : BaseViewModel() {

    fun onNavigateToSave() {
        navigateTo(SetGoalsToSaveScreenKey())
    }
}