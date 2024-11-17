package com.example.core.data

import com.google.gson.annotations.SerializedName

data class ResponseNowPlaying(

    @field:SerializedName("results")
	val results: List<ResultsItemNow>,
)

data class ResultsItemNow(
	@field:SerializedName("poster_path")
	val posterPath: String,

	@field:SerializedName("id")
	val id: Int,
)
