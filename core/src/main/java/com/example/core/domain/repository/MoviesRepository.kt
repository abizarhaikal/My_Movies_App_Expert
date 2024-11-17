package com.example.core.domain.repository

import com.example.core.data.ResultState
import com.example.core.data.local.LocalDataSource
import com.example.core.domain.model.DataMapper
import com.example.core.domain.model.Movies
import com.example.core.domain.model.MoviesNow
import com.example.core.domain.model.SeriesNow
import com.example.core.data.remote.RemoteDataSource
import com.example.core.domain.model.ActorFavorite
import com.example.core.domain.model.CastItemModel
import com.example.core.domain.model.MovieDetail
import com.example.core.domain.model.SeriesDetailModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MoviesRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : IMoviesRepository {
    override suspend fun getTrendingMovies(): Flow<ResultState<List<MoviesNow>>> {
        return remoteDataSource.getTrendingMovies()
    }

    override suspend fun getTrendingTv(): Flow<ResultState<List<SeriesNow>>> {
        return remoteDataSource.getTrendingSeries()
    }

    override suspend fun getTrendingActors(): Flow<ResultState<List<ActorFavorite>>> {
        return remoteDataSource.getTrendingActors()
    }

    override suspend fun getDetailMovies(id: Int): Flow<ResultState<MovieDetail>> {
        return remoteDataSource.getDetailMovies(id)
    }

    override suspend fun getCreditsMovies(id: Int): Flow<ResultState<List<CastItemModel>>> {
        return remoteDataSource.getCreditsMovie(id)
    }

    override suspend fun getDetailSeries(id: Int): Flow<ResultState<SeriesDetailModel>> {
        return remoteDataSource.getDetailSeries(id)
    }

    override suspend fun getCreditsSeries(id: Int): Flow<ResultState<List<CastItemModel>>> {
        return remoteDataSource.getCreditsSeries(id)
    }

    override suspend fun getAllSavedMovies(): Flow<List<Movies>> {
        return localDataSource.getAllSavedMovies().map { moviesEntity ->
            moviesEntity.map { DataMapper.mapEntityToDomain(it) }
        }
    }

    override suspend fun insertMovies(movies: List<Movies>) {
        val moviesEntities = movies.map { DataMapper.mapDomainToEntity(it) }
        localDataSource.insertMovies(moviesEntities)
    }

    override suspend fun deleteMoviesById(id: Int) {
        return localDataSource.deleteMoviesById(id)
    }

    override suspend fun getFavoriteUser(id: Int): Flow<Movies?> {
        return localDataSource.getFavoriteStatus(id).map { moviesEntity ->
            moviesEntity?.let { DataMapper.mapEntityToDomain(it) }
        }
    }

}