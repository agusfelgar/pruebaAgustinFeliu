package com.example.pruebaafg.data.network

import android.util.Log
import com.example.pruebaafg.data.model.ArticleResponse
import com.example.pruebaafg.utils.Constants
import com.example.pruebaafg.utils.RetrofitHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ArticleService {
    private val retrofit = RetrofitHelper.getRetrofit()

    suspend fun getMostViewedArticles(url : String) : ArticleResponse {
        return withContext(Dispatchers.IO) {
            val articles = retrofit.create(APIService::class.java).getMostViewed(url+"api-key=${Constants.APIKEY}")
            articles.body() ?: ArticleResponse(0, listOf())
        }
    }

    suspend fun getMostMailedArticles(url : String) : ArticleResponse {
        return withContext(Dispatchers.IO) {
            val articles = retrofit.create(APIService::class.java).getMostMailed(url+"api-key=${Constants.APIKEY}")
            Log.e("AAA-", articles.toString() + "88888 " +articles.body().toString())
            articles.body() ?: ArticleResponse(0, listOf())
        }
    }

    suspend fun getMostSharedArticles(url : String) : ArticleResponse {
        return withContext(Dispatchers.IO) {
            val articles = retrofit.create(APIService::class.java).getMostShared(url+"api-key=${Constants.APIKEY}")
            Log.e("AAA-", articles.toString() + "88888 " +articles.body().toString())
            articles.body() ?: ArticleResponse(0, listOf())
        }
    }
}