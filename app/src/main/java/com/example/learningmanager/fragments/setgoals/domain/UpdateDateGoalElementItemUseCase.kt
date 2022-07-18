package com.example.learningmanager.fragments.setgoals.domain

import com.example.learningmanager.base.domain.BaseUpdateTwoParamsUseCase
import com.example.learningmanager.fragments.setgoals.data.local.SetGoalsDataDao
import javax.inject.Inject

class UpdateDateGoalElementItemUseCase @Inject constructor(private val goalDao: SetGoalsDataDao): BaseUpdateTwoParamsUseCase<Int, String, Unit>() {
    override suspend fun create(itemId: Int, params: String){
        goalDao.updateOneGoal(itemId, params)
    }
}