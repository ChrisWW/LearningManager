package com.example.learningmanager.fragments.inspirationquotes.data.remote

import com.example.learningmanager.fragments.inspirationquotes.data.InspirationQuotesDetailsResponse
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface InspirationQuotesApi {

    @GET("https://en.wikipedia.org/w/api.php?action=query&generator=search&gsrlimit=1&prop=pageimages%7Cextracts&pithumbsize=400&format=json")
    suspend fun getImage(
        @Query("gsrsearch") name: String
    ): WikipediaPageDetailsResponse

    @POST("http://api.forismatic.com/api/1.0/")
    suspend fun postQuote(
        @Query("method") method: String = "getQuote",
        @Query("format") format: String  = "json",
        @Query("lang") lang: String? = "en",
    ): InspirationQuotesDetailsResponse

    //Why we use @Path?
    @GET("valuepicturesjson")
    suspend fun getInspirationQuote(@Path("pictureId") pictureId: String): InspirationQuotesDetailsResponse

    @GET("/woof.json?ref=apilist.fun")
    suspend fun getRandomPicture(): InspirationQuotesDetailsResponse
}