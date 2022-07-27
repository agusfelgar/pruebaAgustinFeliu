package com.example.pruebaafg.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
data class Article (@SerializedName("title") val title : String, @SerializedName("byline") val autor : String, @SerializedName("section")  val section : String, @SerializedName("published_date") val date : String, @SerializedName("media") val media : List<MediaInfo>, @SerializedName("url") val articleUrl : String) :
    Parcelable