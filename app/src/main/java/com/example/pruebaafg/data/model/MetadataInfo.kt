package com.example.pruebaafg.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class MetadataInfo(@SerializedName("url") val imageUrl : String) : Parcelable
