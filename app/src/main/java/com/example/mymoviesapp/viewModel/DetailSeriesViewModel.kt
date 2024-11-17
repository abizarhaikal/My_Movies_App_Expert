package com.example.mymoviesapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.data.ResultState
import com.example.core.domain.model.CastItemModel
import com.example.core.domain.model.SeriesDetailModel
import com.example.core.domain.usecase.MoviesUseCase
import kotlinx.coroutines.launch

class DetailSeriesViewModel(private val moviesUseCase: MoviesUseCase) : ViewModel() {


    private val _detailSeries = MutableLiveData<SeriesDetailModel>()
    val detailSeries: LiveData<SeriesDetailModel> = _detailSeries

    private val _crewSeries = MutableLiveData<List<CastItemModel>>()
    val crewSeries: LiveData<List<CastItemModel>> = _crewSeries

    private val _isLoading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    fun getCreditSeries(id: Int) {
        _isLoading.value = true
        viewModelScope.launch {
            moviesUseCase.getCreditSeries(id)
                .collect { result ->
                    if (result != null) {
                        when (result) {
                            is ResultState.Success -> {
                                _crewSeries.value = result.data
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

    fun getDetailSeries(id: Int) {
        _isLoading.value = true
        viewModelScope.launch {
            moviesUseCase.getDetailSeries(id)
                .collect { result ->
                    if (result != null) {
                        when (result) {
                            is ResultState.Success -> {
                                _detailSeries.value = result.data
                                _isLoading.value = false
                            }

                            is ResultState.Loading -> {
                                _isLoading.value = true
                            }

                            is ResultState.Error -> {
                                _errorMessage.value = result.error
                            }
                        }
                    }
                }
        }
    }
}