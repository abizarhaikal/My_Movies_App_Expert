package com.example.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Credits(
    val cast : List<CastItemModel>,
    val id : Int
) : Parcelable


@Parcelize
data class CastItemModel(
    val name : String,
    val profilePath : String?,
    val id : Int
) : Parcelable
