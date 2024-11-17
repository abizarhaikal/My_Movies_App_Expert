package com.example.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movies(
    var id: Int,
    var posterPath: String,
    var title: String,
    var overview: String,
    var isFavorite: Boolean
) : Parcelable
