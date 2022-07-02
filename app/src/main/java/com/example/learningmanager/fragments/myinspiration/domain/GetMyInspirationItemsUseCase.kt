package com.example.learningmanager.fragments.myinspiration.domain

import com.example.learningmanager.base.domain.BaseFlowUseCase
import com.example.learningmanager.fragments.myinspiration.data.MyInspirationData
import com.example.learningmanager.fragments.myinspiration.data.local.MyInspirationDataDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetMyInspirationItemsUseCase @Inject constructor(private val myInspirationDataDao: MyInspirationDataDao) :
    BaseFlowUseCase<Unit, List<MyInspirationData>>() {
    override fun create(params: Unit): Flow<List<MyInspirationData>> {
        return flow {
            emit(myInspirationDataDao.getAllInspirations())
        }
    }
}