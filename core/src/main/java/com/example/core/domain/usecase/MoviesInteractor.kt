package com.example.core.domain.usecase

import com.example.core.data.ResponseCredits
import com.example.core.data.ResponseDetailSeries
import com.example.core.data.ResultState
import com.example.core.domain.repository.IMoviesRepository
import com.example.core.domain.model.ActorFavorite
import com.example.core.domain.model.CastItemModel
import com.example.core.domain.model.MovieDetail
import com.example.core.domain.model.Movies
import com.example.core.domain.model.MoviesNow
import com.example.core.domain.model.SeriesDetailModel
import com.example.core.domain.model.SeriesNow
import kotlinx.coroutines.flow.Flow

class MoviesInteractor(private val moviesRepository: IMoviesRepository) : MoviesUseCase {
    override suspend fun getTrendingMovies(): Flow<ResultState<List<MoviesNow>>> {
        return moviesRepository.getTrendingMovies()
    }

    override suspend fun getTrendingSeries(): Flow<ResultState<List<SeriesNow>>> {
        return moviesRepository.getTrendingTv()
    }

    override suspend fun getTrendingActors(): Flow<ResultState<List<ActorFavorite>>> {
        return moviesRepository.getTrendingActors()
    }

    override suspend fun getDetailMovies(id: Int): Flow<ResultState<MovieDetail>> {
        return moviesRepository.getDetailMovies(id)
    }

    override suspend fun getCreditsMovies(id: Int): Flow<ResultState<List<CastItemModel>>> { // Ubah nama dari getCreditMovies menjadi getCreditsMovies
        return moviesRepository.getCreditsMovies(id)
    }

    override suspend fun getDetailSeries(id: Int): Flow<ResultState<SeriesDetailModel>> {
        return moviesRepository.getDetailSeries(id)
    }

    override suspend fun getCreditSeries(id: Int): Flow<ResultState<List<CastItemModel>>> {
        return moviesRepository.getCreditsSeries(id)
    }

    override suspend fun getAllSavedMovies(): Flow<List<Movies>> {
        return moviesRepository.getAllSavedMovies()
    }

    override suspend fun insertMovies(movies: List<Movies>) {
        moviesRepository.insertMovies(movies)
    }

    override suspend fun deleteMoviesById(id: Int) {
        moviesRepository.deleteMoviesById(id)
    }

    override suspend fun getFavoriteUser(id: Int): Flow<Movies?> {
        return moviesRepository.getFavoriteUser(id)
    }
}
