package com.example.mymoviesapp.di

import com.example.core.domain.usecase.MoviesInteractor
import com.example.core.domain.usecase.MoviesUseCase
import com.example.mymoviesapp.viewModel.DetailSeriesViewModel
import com.example.mymoviesapp.viewModel.DetailViewModel
import com.example.mymoviesapp.viewModel.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<MoviesUseCase> { MoviesInteractor(get()) }
}

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { DetailViewModel(get()) }
    viewModel { DetailSeriesViewModel(get()) }
}