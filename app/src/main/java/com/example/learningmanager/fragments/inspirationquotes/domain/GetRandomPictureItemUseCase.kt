package com.example.learningmanager.fragments.inspirationquotes.domain

import com.example.learningmanager.base.domain.BaseApiRequestUseCase
import com.example.learningmanager.base.domain.BaseFlowUseCase
import com.example.learningmanager.base.ext.flowSingle
import com.example.learningmanager.fragments.inspirationquotes.data.InspirationQuotesDetailsResponse
import com.example.learningmanager.fragments.inspirationquotes.data.remote.InspirationQuotesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetRandomPictureItemUseCase @Inject constructor(private val api: InspirationQuotesApi): BaseFlowUseCase<Unit, InspirationQuotesDetailsResponse>() {
    override fun create(params: Unit): Flow<InspirationQuotesDetailsResponse> {
        return flow{
            emit(api.getRandomPicture())
        }
    }
}