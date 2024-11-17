package com.example.core.data

import com.google.gson.annotations.SerializedName

data class ResponseTrendingActors(

    @field:SerializedName("page")
    val page: Int,

    @field:SerializedName("total_pages")
    val totalPages: Int,

    @field:SerializedName("results")
    val results: List<ResultsItemActors>,

    @field:SerializedName("total_results")
    val totalResults: Int
)

data class ResultsItemActors(


    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("profile_path")
    val profilePath: String?,

    @field:SerializedName("id")
    val id: Int,
)

