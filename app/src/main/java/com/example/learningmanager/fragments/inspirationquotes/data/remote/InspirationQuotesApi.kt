package com.example.learningmanager.fragments.inspirationquotes.data.remote

import com.example.learningmanager.fragments.inspirationquotes.data.InspirationQuotesData
import com.example.learningmanager.fragments.inspirationquotes.data.InspirationQuotesDetailsResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface InspirationQuotesApi {

    //Why we use @Path?
    @GET("valuepicturesjson")
    suspend fun getInspirationQuote(@Path("pictureId") pictureId: String): InspirationQuotesDetailsResponse

    @GET("/woof.json?ref=apilist.fun")
    suspend fun getRandomPicture(): InspirationQuotesDetailsResponse
}

