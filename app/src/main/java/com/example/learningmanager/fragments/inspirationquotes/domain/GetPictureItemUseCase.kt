package com.example.learningmanager.fragments.inspirationquotes.domain

import com.example.learningmanager.base.domain.BaseApiRequestUseCase
import com.example.learningmanager.base.ext.flowSingle
import com.example.learningmanager.fragments.inspirationquotes.data.InspirationQuotesDetailsResponse
import com.example.learningmanager.fragments.inspirationquotes.data.remote.InspirationQuotesApi
import javax.inject.Inject

class GetPictureItemUseCase @Inject constructor(private val api: InspirationQuotesApi) :
    BaseApiRequestUseCase<GetPictureItemUseCase.Params, InspirationQuotesDetailsResponse>() {

    override fun create(params: Params) = flowSingle {
        api.getInspirationQuote(params.pictureId)
    }

    data class Params(val pictureId: String)
}