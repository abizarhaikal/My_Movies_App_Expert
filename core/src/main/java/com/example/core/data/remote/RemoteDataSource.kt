package com.example.core.data.remote

import android.util.Log
import com.example.core.data.ResponseCredits
import com.example.core.data.ResponseDetailSeries
import com.example.core.data.ResultState
import com.example.core.domain.model.ActorFavorite
import com.example.core.domain.model.CastItemModel
import com.example.core.domain.model.DataMapper
import com.example.core.domain.model.MovieDetail
import com.example.core.domain.model.MoviesNow
import com.example.core.domain.model.SeriesDetailModel
import com.example.core.domain.model.SeriesNow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource(private val apiService: ApiService) {

    suspend fun getTrendingMovies(): Flow<ResultState<List<MoviesNow>>> {
        return flow {
            try {
                val response = apiService.getTrendingMovies()
                val dataArray = response.results
                if (dataArray.isNotEmpty()) {
                    val domainMovies =
                        DataMapper.mapResponseToDomain(dataArray) // Menggunakan DataMapper
                    emit(ResultState.Success(domainMovies))
                } else {
                    emit(ResultState.Loading)
                }
            } catch (e: Exception) {
                emit(ResultState.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }


    suspend fun getTrendingSeries(): Flow<ResultState<List<SeriesNow>>> {
        return flow {
            try {
                val response = apiService.getTrendingSeries()
                val dataArray = response.results.take(10)
                if (dataArray.isNotEmpty()) {
                    val domainSeries = DataMapper.mapResponseToDomainSeries(dataArray)
                    emit(ResultState.Success(domainSeries))
                } else {
                    emit(ResultState.Loading)
                }
            } catch (e: Exception) {
                emit(ResultState.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getTrendingActors(): Flow<ResultState<List<ActorFavorite>>> {
        return flow {
            try {
                val response = apiService.getTrendingActors()
                val dataArray = response.results.take(10)
                if (dataArray.isNotEmpty()) {
                    val domainFavorite = DataMapper.mapResponseToDomainActorPopular(dataArray)
                    emit(ResultState.Success(domainFavorite))
                } else {
                    emit(ResultState.Loading)
                }
            } catch (e: Exception) {
                emit(ResultState.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getDetailMovies(id: Int): Flow<ResultState<MovieDetail>> {
        return flow {
            try {
                val response = apiService.getDetailMovies(id)
                val domainDetail = DataMapper.mapResponseToDomainDetail(response)
                emit(ResultState.Success(domainDetail))
            } catch (e: Exception) {
                emit(ResultState.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getCreditsMovie(id: Int): Flow<ResultState<List<CastItemModel>>> {
        return flow {
            try {
                // Mendapatkan response dari API
                val response = apiService.getCredits(id)
                // Memetakan response ke domain - pastikan memetakan ke List
                val domainCastList = DataMapper.mapResponseToDomainCast(response) // Pastikan ini mengembalikan List<CastItemModel>
                emit(ResultState.Success(domainCastList)) // Emit list cast dari objek Credits
            } catch (e: Exception) {
                emit(ResultState.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO) // Mengalihkan eksekusi ke IO Dispatcher
    }




    suspend fun getDetailSeries(id: Int): Flow<ResultState<SeriesDetailModel>> {
        return flow {
            try {
                val response = apiService.getDetailSeries(id)
                val domainDetail = DataMapper.mapResponseToDomainSeries(response)
                emit(ResultState.Success(domainDetail))
            } catch (e: Exception) {
                emit(ResultState.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getCreditsSeries(id: Int): Flow<ResultState<List<CastItemModel>>> {
        return flow {
            try {
                // Mendapatkan response dari API
                val response = apiService.getCreditsSeries(id)
                // Memetakan response ke domain - pastikan memetakan ke List
                val domainCastList = DataMapper.mapResponseToDomainCast(response) // Pastikan ini mengembalikan List<CastItemModel>
                emit(ResultState.Success(domainCastList)) // Emit list cast dari objek Credits
            } catch (e: Exception) {
                emit(ResultState.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

}
