package com.example.learningmanager.fragments

import androidx.lifecycle.viewModelScope
import com.example.learningmanager.base.ui.BaseViewModel
import com.example.learningmanager.fragments.myinspiration.FirebaseManager.Companion.setGoalsData
import com.example.learningmanager.fragments.setgoals.data.SetGoalsData
import com.example.learningmanager.fragments.setgoals.domain.GetGoalsItemDetailsUseCase
import com.example.learningmanager.fragments.setgoals.domain.UpdateDateGoalElementItemUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewPagerViewModel @Inject constructor(
    private val getGoalsItemDetailsUseCase: GetGoalsItemDetailsUseCase
    ) :
    BaseViewModel() {
    val isGoalListEmptyFlow = MutableStateFlow(true)
    val setGoalsData = MutableStateFlow<List<SetGoalsData>>(emptyList())


    fun getActualGoalsData() {
        viewModelScope.launch {
            getGoalsItemDetailsUseCase.build(Unit).collect {
                setGoalsData.value = it
            }
        }
    }

    fun isGoalListEmpty() {
        viewModelScope.launch {
            getGoalsItemDetailsUseCase.build(Unit).collect {
                if (it.isEmpty()) {
                    isGoalListEmptyFlow.value = true
                } else {
                    it.forEach { list ->
                        if (!list.isDone) {
                            isGoalListEmptyFlow.value = false
                            return@forEach
                        } else {
                            isGoalListEmptyFlow.value = true
                        }
                    }
                }
            }
        }
    }
}