package com.example.news

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsAPIs
{

    @GET("v2/everything")
    fun getTopNews(
        @Query("q") q: String = "top india news",
        @Query("apiKey") key: String = "d2be7aab5e8a48b888fbcced4315433f"
    ): Call<NewsAPIResponse>

    @GET("v2/everything")
    fun getNewsAbout(
        @Query("q") q: String,
        @Query("apiKey") key: String = "d2be7aab5e8a48b888fbcced4315433f"
    ): Call<NewsAPIResponse>
}