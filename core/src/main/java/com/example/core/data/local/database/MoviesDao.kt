package com.example.core.data.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.core.data.local.MoviesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MoviesDao {
    @Query("SELECT * FROM moviesEntity")
    suspend fun getAllMovies(): List<MoviesEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<MoviesEntity>)

    @Query("DELETE FROM moviesEntity WHERE id = :id")
    suspend fun deleteMoviesById(id: Int)

    @Query("SELECT * FROM moviesEntity WHERE id = :id")
    fun getFavoriteMoviesById(id: Int) : Flow<MoviesEntity?>
}