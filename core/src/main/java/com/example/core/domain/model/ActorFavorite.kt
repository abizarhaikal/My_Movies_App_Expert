package com.example.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ActorFavorite(
    val id: Int,
    val name: String,
    val profilePath: String?,
) : Parcelable
