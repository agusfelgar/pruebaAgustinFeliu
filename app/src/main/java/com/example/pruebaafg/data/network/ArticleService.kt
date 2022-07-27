package com.example.pruebaafg.data.network

import android.util.Log
import com.example.pruebaafg.data.model.ArticleResponse
import com.example.pruebaafg.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ArticleService @Inject constructor(private val api: APIService, private val constants : Constants){

    suspend fun getMostViewedArticles(url : String) : ArticleResponse? {
        return withContext(Dispatchers.IO) {
            val articles = api.getMostViewed(url+"api-key=${constants.APIKEY}")
            articles.body()
        }
    }

    suspend fun getMostMailedArticles(url : String) : ArticleResponse? {
        return withContext(Dispatchers.IO) {
            val articles = api.getMostMailed(url+"api-key=${constants.APIKEY}")
            articles.body()
        }
    }

    suspend fun getMostSharedArticles(url : String) : ArticleResponse? {
        return withContext(Dispatchers.IO) {
            val articles = api.getMostShared(url+"api-key=${constants.APIKEY}")
            articles.body()
        }
    }
}