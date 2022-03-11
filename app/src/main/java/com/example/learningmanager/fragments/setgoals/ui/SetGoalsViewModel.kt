package com.example.learningmanager.fragments.setgoals.ui

import androidx.lifecycle.viewModelScope
import com.example.learningmanager.base.ui.BaseViewModel
import com.example.learningmanager.fragments.setgoals.data.SetGoalsData
import com.example.learningmanager.fragments.setgoals.domain.GetGoalsItemDetailsUseCase
import com.example.learningmanager.fragments.setgoals.route.SetGoalsToSaveScreenKey
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SetGoalsViewModel @Inject constructor(
    private val getGoalsItemDetailsUseCase: GetGoalsItemDetailsUseCase
) : BaseViewModel() {
    val setGoalsData = MutableStateFlow<List<SetGoalsData>>(emptyList())

    init {
        getActualState()
    }

    private fun getActualState() {
        viewModelScope.launch {
            getGoalsItemDetailsUseCase.build(Unit).collect {
                setGoalsData.value = it
            }
        }
    }

    fun onNavigateToSave() {
        navigateTo(SetGoalsToSaveScreenKey())
    }

    fun deleteItem(id: Int) {
        viewModelScope.launch {
            //usecase with id
            getActualState()
        }
    }

    fun onItemClicked(id: Int) {
        // navigaton to root
    }
}