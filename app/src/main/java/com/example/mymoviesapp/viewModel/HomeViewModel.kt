package com.example.mymoviesapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.data.ResultState
import com.example.core.domain.model.ActorFavorite
import com.example.core.domain.model.MoviesNow
import com.example.core.domain.model.SeriesNow
import com.example.core.domain.usecase.MoviesUseCase
import kotlinx.coroutines.launch

class HomeViewModel(private val moviesUseCase: MoviesUseCase) : ViewModel() {

    private val _trendingMovies = MutableLiveData<List<MoviesNow>>()
    val trendingMovies: LiveData<List<MoviesNow>> = _trendingMovies

    private val _trendingSeries = MutableLiveData<List<SeriesNow>>()
    val trendingSeries: LiveData<List<SeriesNow>> = _trendingSeries

    private val _trendingActors = MutableLiveData<List<ActorFavorite>>()
    val trendingActors: LiveData<List<ActorFavorite>> = _trendingActors

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        getTrendingMovies()
        getTrendingSeries()
        getTrendingActors()

    }

    fun getTrendingActors() {
        _isLoading.value = true
        viewModelScope.launch {
            moviesUseCase.getTrendingActors()
                .collect { result ->
                    if (result != null) {
                        when (result) {
                            is ResultState.Success -> {
                                _trendingActors.value = result.data
                                _isLoading.value = false
                            }

                            is ResultState.Loading -> {
                                _isLoading.value = true
                            }

                            is ResultState.Error -> {
                                _isLoading.value = false
                            }
                        }
                    }
                }
        }
    }

    fun getTrendingSeries() {
        _isLoading.value = true
        viewModelScope.launch {
            moviesUseCase.getTrendingSeries()
                .collect { result ->
                    if (result != null) {
                        when (result) {
                            is ResultState.Success -> {
                                _trendingSeries.value = result.data
                                _isLoading.value = false
                            }

                            is ResultState.Loading -> {
                                _isLoading.value = true
                            }

                            is ResultState.Error -> {
                                _isLoading.value = false
                            }
                        }
                    }
                }
        }
    }


    fun getTrendingMovies() {
        _isLoading.value = true
        viewModelScope.launch {
            moviesUseCase.getTrendingMovies()
                .collect { result ->
                    if (result != null) {
                        when (result) {
                            is ResultState.Success -> {
                                _trendingMovies.value = result.data
                                _isLoading.value = false
                            }

                            is ResultState.Loading -> {
                                _isLoading.value = true
                            }

                            is ResultState.Error -> {
                                _isLoading.value = false
                            }
                        }
                    }
                }
        }

    }
}