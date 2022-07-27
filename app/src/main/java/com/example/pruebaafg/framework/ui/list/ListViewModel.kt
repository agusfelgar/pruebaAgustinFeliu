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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val getMostMailedArticlesUseCase : GetMostMailedArticlesUseCase,
    private val getMostSharedArticlesUseCase : GetMostSharedArticlesUseCase,
    private val getMostViewedArticlesUseCase : GetMostViewedArticlesUseCase
) : ViewModel() {

    private val _articleList = MutableLiveData<List<Article>>()
    val articleList : LiveData<List<Article>> get() = _articleList

    private val _loadingVisibility = MutableLiveData<Boolean>()
    val loadingVisibility : LiveData<Boolean> get() = _loadingVisibility

    private val _errorVisibility = MutableLiveData<Boolean>()
    val errorVisibility : LiveData<Boolean> get() = _errorVisibility

    private val _noResultVisibility = MutableLiveData<Boolean>()
    val noResultVisibility : LiveData<Boolean> get() = _noResultVisibility

    fun onInit(type : TypeEnum, period : PeriodEnum, facebook: Boolean, twitter : Boolean) {
        _noResultVisibility.value = false

        viewModelScope.launch {
            val result = when (type) {
                TypeEnum.MOSTMAILED -> getMostMailedArticlesUseCase(period)
                TypeEnum.MOSTSHARED -> getMostSharedArticlesUseCase(period, facebook, twitter)
                TypeEnum.MOSTVIEWED -> getMostViewedArticlesUseCase(period)
                TypeEnum.NONE -> ArticleResponse(0, listOf())
            }

            if (result != null) {
                _noResultVisibility.value = false
                if(result?.num_results != 0) {
                    _errorVisibility.value = false
                    _articleList.postValue(result?.articles)
                    _loadingVisibility.value = false
                } else {
                    _noResultVisibility.value = true
                }
            } else {
                _errorVisibility.value = true
                _loadingVisibility.value = false
            }

        }
    }
}