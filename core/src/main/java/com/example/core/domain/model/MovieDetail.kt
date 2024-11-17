package com.example.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieDetail(
    val id: Int,
    val title: String,
    val revenue: Int,
    val genres: List<Genre>,
    val popularity: Double,
    val productionCountries: List<ProductionCountry>,
    val overview: String,
    val runtime: Int,
    val posterPath: String?,
    val releaseDate: String,
    val homepage: String

) : Parcelable

@Parcelize
data class Genre(
    val id: Int,
    val name: String
) : Parcelable

@Parcelize
data class ProductionCountry(
    val iso31661: String,
    val name: String
) : Parcelable

@Parcelize
data class SeriesDetailModel(
    val genres: List<Genre>,
    val popularity: Double,
    val productionCountries: List<ProductionCountry>,
    val id: Int,
    val firstAirDate: String,
    val overview: String,
    val posterPath: String,
    val name: String,
    val episodeRunTime: List<Int>,
    val homepage: String
) : Parcelable
