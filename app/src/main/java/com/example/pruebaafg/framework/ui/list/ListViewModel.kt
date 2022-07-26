package com.example.pruebaafg.framework.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pruebaafg.data.model.Article
import com.example.pruebaafg.data.model.ArticleResponse
import com.example.pruebaafg.data.model.PeriodEnum
import com.example.pruebaafg.data.model.TypeEnum
import com.example.pruebaafg.domain.usecases.GetMostMailedArticlesUseCase
import com.example.pruebaafg.domain.usecases.GetMostSharedArticlesUseCase
import com.example.pruebaafg.domain.usecases.GetMostViewedArticlesUseCase
import kotlinx.coroutines.launch

class ListViewModel : ViewModel() {

    private val _articleList = MutableLiveData<List<Article>>()
    val articleList : LiveData<List<Article>> get() = _articleList

    private val _loadingVisibility = MutableLiveData<Boolean>()
    val loadingVisibility : LiveData<Boolean> get() = _loadingVisibility

    private val _errorVisibility = MutableLiveData<Boolean>()
    val errorVisibility : LiveData<Boolean> get() = _errorVisibility

    fun onInit(type : TypeEnum, period : PeriodEnum, facebook: Boolean, twitter : Boolean) {

        var getMostMailedArticlesUseCase = GetMostMailedArticlesUseCase()
        var getMostSharedArticlesUseCase = GetMostSharedArticlesUseCase()
        var getMostViewedArticlesUseCase = GetMostViewedArticlesUseCase()

        viewModelScope.launch {
            val result = when (type) {
                TypeEnum.MOSTMAILED -> getMostMailedArticlesUseCase(period)
                TypeEnum.MOSTSHARED -> getMostSharedArticlesUseCase(period, facebook, twitter)
                TypeEnum.MOSTVIEWED -> getMostViewedArticlesUseCase(period)
                TypeEnum.NONE -> ArticleResponse(0, listOf())
            }
            if(result?.num_results != 0) {
                _errorVisibility.value = false
                _articleList.postValue(result?.articles)
                _loadingVisibility.value = false
            } else {
                _errorVisibility.value = true
                _loadingVisibility.value = false
            }
        }
    }
}