package com.example.pruebaafg.framework.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pruebaafg.data.model.Article

class DetailViewModel : ViewModel() {

    private val _articleUrl = MutableLiveData<String>()
    val articleUrl : LiveData<String> get() = _articleUrl

    fun onInit(article : Article) {
        _articleUrl.value = article.articleUrl
    }
}