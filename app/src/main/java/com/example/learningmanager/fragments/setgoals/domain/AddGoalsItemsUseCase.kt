package com.example.learningmanager.fragments.setgoals.domain

import com.example.learningmanager.fragments.setgoals.data.SetGoalsData
import com.example.learningmanager.fragments.setgoals.data.SetGoalsDataDetailsResponse
import com.example.learningmanager.fragments.setgoals.data.local.SetGoalsDataDao
import javax.inject.Inject

class AddGoalsItemsUseCase @Inject constructor(private val setGoalsDataDao: SetGoalsDataDao) {
    suspend fun create(params: SetGoalsDataDetailsResponse) {
        val setGoalsData = SetGoalsData(
            params.id,
            params.goal,
            params.intenseGoal,
            params.timeGoal,
            params.date,
            params.color
        )
        setGoalsDataDao.addGoal(setGoalsData)
    }
}