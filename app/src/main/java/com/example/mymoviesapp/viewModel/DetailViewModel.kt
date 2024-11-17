package com.example.mymoviesapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.data.ResultState
import com.example.core.domain.model.CastItemModel
import com.example.core.domain.model.MovieDetail
import com.example.core.domain.model.Movies
import com.example.core.domain.usecase.MoviesUseCase
import kotlinx.coroutines.launch

class DetailViewModel(private val moviesUseCase: MoviesUseCase) : ViewModel() {

    private val _detailMovies = MutableLiveData<MovieDetail?>()
    val detailMovies: LiveData<MovieDetail?> get() = _detailMovies

    private val _crewMovies = MutableLiveData<List<CastItemModel>?>()
    val crewMovies: LiveData<List<CastItemModel>?> get() = _crewMovies

    private val _favoriteMovie = MutableLiveData<Movies?>()
    val favoriteMovie: LiveData<Movies?> get() = _favoriteMovie

    private val _isLoading = MutableLiveData<Boolean>()

    private val _errorMessage = MutableLiveData<String?>()

    fun getCreditMovies(id: Int) {
        _isLoading.value = true
        viewModelScope.launch {
            moviesUseCase.getCreditsMovies(id)
                .collect { result ->
                    if (result != null) {
                        when (result) {
                            is ResultState.Success -> {
                                _crewMovies.value = result.data
                                _isLoading.value = false
                            }

                            is ResultState.Error -> {
                                _errorMessage.value = result.error
                                _isLoading.value = false
                            }

                            is ResultState.Loading -> {
                                _isLoading.value = true
                            }
                        }
                    }
                }
        }
    }

    fun getDetailMovies(id: Int) {
        _isLoading.value = true
        viewModelScope.launch {
            moviesUseCase.getDetailMovies(id)
                .collect { result ->
                    if (result != null) {
                        when (result) {
                            is ResultState.Success -> {
                                _detailMovies.value = result.data
                                _isLoading.value = false
                            }

                            is ResultState.Loading -> {
                                _isLoading.value = true
                            }

                            is ResultState.Error -> {
                                _errorMessage.value = result.error
                                _isLoading.value = false
                            }
                        }
                    }
                }
        }
    }

    fun insertMovies(movies: Movies) {
        viewModelScope.launch {
            moviesUseCase.insertMovies(listOf(movies))
        }
    }

    fun deleteMovies(id: Int) {
        viewModelScope.launch {
            moviesUseCase.deleteMoviesById(id)
        }
    }

    fun getFavoriteUser(id: Int) {
        viewModelScope.launch {
            moviesUseCase.getFavoriteUser(id).collect { favoriteMovie ->
                _favoriteMovie.postValue(favoriteMovie)
            }
        }
    }
}
