package com.example.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SeriesNow(
    val id : Int,
    val posterPath: String,
) : Parcelable