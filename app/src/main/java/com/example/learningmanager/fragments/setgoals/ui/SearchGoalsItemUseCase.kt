package com.example.learningmanager.fragments.setgoals.ui

import com.example.learningmanager.base.domain.BaseFlowUseCase
import com.example.learningmanager.fragments.setgoals.data.SetGoalsData
import com.example.learningmanager.fragments.setgoals.data.local.SetGoalsDataDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchGoalsItemUseCase @Inject constructor(private val dao: SetGoalsDataDao) :
    BaseFlowUseCase<String, List<SetGoalsData>>() {
    public override fun create(query: String): Flow<List<SetGoalsData>> {
        return flow {
            emit(dao.searchGoal(query))
        }
    }
}