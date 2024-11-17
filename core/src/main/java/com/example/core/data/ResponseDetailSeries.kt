package com.example.core.data

import com.google.gson.annotations.SerializedName

data class ResponseDetailSeries(

    @field:SerializedName("genres")
    val genres: List<GenresItemSeries>,

    @field:SerializedName("popularity")
    val popularity: Double,

    @field:SerializedName("production_countries")
    val productionCountries: List<ProductionCountriesItem>,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("first_air_date")
    val firstAirDate: String,

    @field:SerializedName("overview")
    val overview: String,


    @field:SerializedName("poster_path")
    val posterPath: String,


    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("episode_run_time")
    val episodeRunTime: List<Int>,


    @field:SerializedName("homepage")
    val homepage: String,

    )