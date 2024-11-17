package com.example.core.data.local

import com.example.core.data.local.database.MoviesDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class LocalDataSource(private val moviesDao: MoviesDao) {
    suspend fun getAllSavedMovies(): Flow<List<MoviesEntity>> {
        return flow {
            val savedMovies = moviesDao.getAllMovies()
            emit(savedMovies)
        }.flowOn(Dispatchers.IO)
    }

    suspend fun insertMovies(movies: List<MoviesEntity>) {
        moviesDao.insertMovies(movies)
    }

    suspend fun deleteMoviesById(id: Int) {
        moviesDao.deleteMoviesById(id)
    }

    fun getFavoriteStatus(id: Int): Flow<MoviesEntity?> {
        return moviesDao.getFavoriteMoviesById(id).map { it }
    }
}