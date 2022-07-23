package com.example.learningmanager.fragments.setgoals.domain

import com.example.learningmanager.base.domain.BaseFlowUseCase
import com.example.learningmanager.fragments.setgoals.data.SetGoalsData
import com.example.learningmanager.fragments.setgoals.data.local.SetGoalsDataDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetGoalsItemDetailsUseCase @Inject constructor(private val setGoalsDataDao: SetGoalsDataDao) :
    BaseFlowUseCase<Unit, List<SetGoalsData>>() {
    override fun create(params: Unit): Flow<List<SetGoalsData>> {
        return flow {
            emit(setGoalsDataDao.getAllGoals())
        }
    }
}