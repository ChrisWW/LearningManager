package com.example.learningmanager.fragments.inspirationquotes.domain

import com.example.learningmanager.base.domain.BaseFlowOneParameterUseCase
import com.example.learningmanager.base.domain.BaseFlowUseCase
import com.example.learningmanager.fragments.inspirationquotes.data.InspirationQuotesDetailsResponse
import com.example.learningmanager.fragments.inspirationquotes.data.remote.InspirationQuotesApi
import com.example.learningmanager.fragments.inspirationquotes.data.remote.WikipediaPageDetailsResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetImageQuotePictureItemUseCase @Inject constructor(private val api: InspirationQuotesApi): BaseFlowOneParameterUseCase<String, Unit, WikipediaPageDetailsResponse>() {
    override fun create(name: String, params: Unit): Flow<WikipediaPageDetailsResponse> {
        return flow{
            emit(api.getImage(name))
        }
    }
}