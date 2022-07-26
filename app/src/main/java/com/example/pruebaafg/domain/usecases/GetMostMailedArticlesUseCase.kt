package com.example.pruebaafg.domain.usecases

import com.example.pruebaafg.data.model.ArticleResponse
import com.example.pruebaafg.data.model.PeriodEnum
import com.example.pruebaafg.data.repository.ArticleRepository

class GetMostMailedArticlesUseCase {
    private val repository = ArticleRepository()
    suspend operator fun invoke(period : PeriodEnum): ArticleResponse? = repository.getMostMailedArticles(period)
}