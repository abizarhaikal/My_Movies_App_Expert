package com.example.core.data

import com.google.gson.annotations.SerializedName

data class ResponseDetailMovie(




	@field:SerializedName("title")
	val title: String,


	@field:SerializedName("revenue")
	val revenue: Int,

	@field:SerializedName("genres")
	val genres: List<GenresItemSeries>,

	@field:SerializedName("popularity")
	val popularity: Double,

	@field:SerializedName("production_countries")
	val productionCountries: List<ProductionCountriesItem>,

	@field:SerializedName("id")
	val id: Int,


	@field:SerializedName("overview")
	val overview: String,


	@field:SerializedName("runtime")
	val runtime: Int,

	@field:SerializedName("poster_path")
	val posterPath: String,


	@field:SerializedName("release_date")
	val releaseDate: String,


	@field:SerializedName("homepage")
	val homepage: String,

)


data class GenresItemSeries(

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("id")
	val id: Int
)


data class ProductionCountriesItem(

	@field:SerializedName("iso_3166_1")
	val iso31661: String,

	@field:SerializedName("name")
	val name: String
)
