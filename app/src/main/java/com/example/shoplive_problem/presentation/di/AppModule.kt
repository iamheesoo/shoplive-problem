package com.example.shoplive_problem.presentation.di

import com.example.shoplive_problem.presentation.favorite.FavoriteViewModel
import com.example.shoplive_problem.presentation.search.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

private val viewModelModule = module {
    viewModel { SearchViewModel() }
    viewModel { FavoriteViewModel() }
}

val appModule = viewModelModule