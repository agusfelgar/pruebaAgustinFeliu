package com.example.pruebaafg.framework.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pruebaafg.data.model.Article
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(): ViewModel() {

    private val _articleUrl = MutableLiveData<String>()
    val articleUrl : LiveData<String> get() = _articleUrl

    fun onInit(article : Article) {
        _articleUrl.value = article.articleUrl
    }
}