package com.example.core.domain.usecase

import com.example.core.data.ResultState
import com.example.core.domain.model.ActorFavorite
import com.example.core.domain.model.CastItemModel
import com.example.core.domain.model.MovieDetail
import com.example.core.domain.model.Movies
import com.example.core.domain.model.MoviesNow
import com.example.core.domain.model.SeriesDetailModel
import com.example.core.domain.model.SeriesNow
import kotlinx.coroutines.flow.Flow

interface MoviesUseCase {
    suspend fun getTrendingMovies(): Flow<ResultState<List<MoviesNow>>>

    suspend fun getTrendingSeries(): Flow<ResultState<List<SeriesNow>>>

    suspend fun getTrendingActors(): Flow<ResultState<List<ActorFavorite>>>

    suspend fun getDetailMovies(id: Int) : Flow<ResultState<MovieDetail>>

    suspend fun getCreditsMovies(id: Int): Flow<ResultState<List<CastItemModel>>>

    suspend fun getDetailSeries(id: Int) : Flow<ResultState<SeriesDetailModel>>

    suspend fun getCreditSeries(id: Int) : Flow<ResultState<List<CastItemModel>>>

    //    GET DATA FROM DATABASE
    suspend fun getAllSavedMovies() : Flow<List<Movies>>

    suspend fun insertMovies(movies: List<Movies>)

    suspend fun deleteMoviesById(id: Int)

    suspend fun getFavoriteUser(id: Int) : Flow<Movies?>
}