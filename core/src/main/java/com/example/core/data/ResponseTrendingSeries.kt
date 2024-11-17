package com.example.core.data

import com.google.gson.annotations.SerializedName

data class ResponseTrendingSeries(

    @field:SerializedName("results")
	val results: List<ResultsItemSeries>,

)

data class ResultsItemSeries(
	@field:SerializedName("poster_path")
	val posterPath: String,

	@field:SerializedName("id")
	val id: Int,

)
