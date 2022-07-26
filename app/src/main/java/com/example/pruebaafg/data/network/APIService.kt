package com.example.pruebaafg.data.network

import com.example.pruebaafg.data.model.ArticleResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface APIService {
    @GET
    suspend fun getMostViewed(@Url url:String): Response<ArticleResponse>

    @GET
    suspend fun getMostShared(@Url url:String): Response<ArticleResponse>

    @GET
    suspend fun getMostMailed(@Url url:String): Response<ArticleResponse>

}