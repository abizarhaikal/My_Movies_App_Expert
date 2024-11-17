package com.example.mymoviesapp.favorite.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.domain.model.Movies
import com.example.core.domain.usecase.MoviesUseCase
import kotlinx.coroutines.launch

class FavoriteViewModel(private val moviesUseCase: MoviesUseCase) : ViewModel() {

    private val _getFavoriteMovies = MutableLiveData<List<Movies?>>()
    val getFavoriteMovies: MutableLiveData<List<Movies?>> = _getFavoriteMovies


    fun getAllUser() {
        viewModelScope.launch {
            moviesUseCase.getAllSavedMovies().collect { favoriteMovie ->
                _getFavoriteMovies.value = favoriteMovie
            }
        }
    }

}