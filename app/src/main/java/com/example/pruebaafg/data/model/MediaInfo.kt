package com.example.pruebaafg.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class MediaInfo(
    @SerializedName("media-metadata") val mediaMetaData : List<MetadataInfo>) : Parcelable
