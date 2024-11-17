package com.example.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MoviesNow (
    val posterPath: String,
    val id: Int,
) : Parcelable