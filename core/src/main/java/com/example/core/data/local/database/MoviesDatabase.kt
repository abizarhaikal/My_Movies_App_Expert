package com.example.core.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.core.data.local.MoviesEntity

@Database(entities = [MoviesEntity::class], exportSchema = false, version = 1)
abstract class MoviesDatabase : RoomDatabase() {
    abstract fun moviesDao() : MoviesDao
}