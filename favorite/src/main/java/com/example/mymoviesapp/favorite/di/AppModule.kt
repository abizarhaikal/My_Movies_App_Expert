package com.example.mymoviesapp.favorite.di

import com.example.mymoviesapp.favorite.viewModel.FavoriteViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val favoriteModule = module {

    viewModel { FavoriteViewModel(get()) }
}