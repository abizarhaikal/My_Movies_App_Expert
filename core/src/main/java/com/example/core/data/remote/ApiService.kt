package com.example.core.data.remote

import com.example.core.data.ResponseCredits
import com.example.core.data.ResponseDetailMovie
import com.example.core.data.ResponseDetailSeries
import com.example.core.data.ResponseNowPlaying
import com.example.core.data.ResponseTrendingActors
import com.example.core.data.ResponseTrendingSeries
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("movie/now_playing?language=en-US")
    suspend fun getTrendingMovies(): ResponseNowPlaying

    @GET("trending/tv/day?language=en-US")
    suspend fun getTrendingSeries(): ResponseTrendingSeries

    @GET("trending/person/day?language=en-US")
    suspend fun getTrendingActors(): ResponseTrendingActors

    @GET("movie/{id}language=en-US")
    suspend fun getDetailMovies(
        @Path("id") id: Int
    ): ResponseDetailMovie

    @GET("movie/{id}/credits?language=en-US")
    suspend fun getCredits(
        @Path("id") id: Int
    ): ResponseCredits

    @GET("tv/{id}?language=en-US")
    suspend fun getDetailSeries(
        @Path("id") id: Int
    ): ResponseDetailSeries

    @GET("tv/{id}/credits?language=en-US")
    suspend fun getCreditsSeries(
        @Path("id") id: Int
    ): ResponseCredits
}