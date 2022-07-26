package com.example.pruebaafg.data.repository

import com.example.pruebaafg.data.model.ArticleResponse
import com.example.pruebaafg.data.model.PeriodEnum
import com.example.pruebaafg.data.network.ArticleService

class ArticleRepository {

    private val api = ArticleService()

    suspend fun getMostViewedArticles(period : PeriodEnum): ArticleResponse {
        val mPeriod = when(period) {
            PeriodEnum.NONE -> 0
            PeriodEnum.ONE_DAY -> 1
            PeriodEnum.SEVEN_DAYS -> 7
            PeriodEnum.THIRTY_DAYS -> 30
        }
        val response = api.getMostViewedArticles("mostviewed/all-sections/$mPeriod.json?")
        return response
    }

    suspend fun getMostSharedArticles(period : PeriodEnum, facebook : Boolean, twitter : Boolean):ArticleResponse{
        val mPeriod = when(period) {
            PeriodEnum.NONE -> 0
            PeriodEnum.ONE_DAY -> 1
            PeriodEnum.SEVEN_DAYS -> 7
            PeriodEnum.THIRTY_DAYS -> 30
        }
        var url = "mostshared/all-sections/"
        var isFacebook = false;
        var isTwitter = false;
        if (facebook) {
            isFacebook = true
            url = url + "facebook"
        }
        if (twitter) {
            isTwitter = true
            if (isFacebook) {
                url = url+ ";"
            }
            url += "twitter"
        }
        if (isFacebook || isTwitter) {
            url += "/"
        }
        url += "$mPeriod.json?"
        val response = api.getMostSharedArticles(url)
        return response
    }

    suspend fun getMostMailedArticles(period : PeriodEnum): ArticleResponse {
        val mPeriod = when(period) {
            PeriodEnum.NONE -> 0
            PeriodEnum.ONE_DAY -> 1
            PeriodEnum.SEVEN_DAYS -> 7
            PeriodEnum.THIRTY_DAYS -> 30
        }
        val response = api.getMostMailedArticles("mostmailed/all-sections/$mPeriod.json?")

        return response
    }
}