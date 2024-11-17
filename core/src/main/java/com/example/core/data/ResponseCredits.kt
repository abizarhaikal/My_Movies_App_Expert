package com.example.core.data

import com.google.gson.annotations.SerializedName

data class ResponseCredits(

	@field:SerializedName("cast")
	val cast: List<CastItem>,

	@field:SerializedName("id")
	val id: Int,

)

data class CastItem(

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("profile_path")
	val profilePath: String?,

	@field:SerializedName("id")
	val id: Int,

)
