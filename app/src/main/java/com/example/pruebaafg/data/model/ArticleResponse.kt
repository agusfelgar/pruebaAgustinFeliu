package com.example.pruebaafg.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
data class ArticleResponse (@SerializedName("num_results") val num_results : Int, @SerializedName("results") val articles : List<Article>) :
    Parcelable