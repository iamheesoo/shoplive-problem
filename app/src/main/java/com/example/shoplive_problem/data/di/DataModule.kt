package com.example.shoplive_problem.data.di

import com.example.shoplive_problem.data.api.MarvelApi
import com.example.shoplive_problem.data.datasource.CharacterLocalDataSource
import com.example.shoplive_problem.data.datasource.CharacterLocalDataSourceImpl
import com.example.shoplive_problem.data.datasource.MarvelRemoteDataSource
import com.example.shoplive_problem.data.datasource.MarvelRemoteDataSourceImpl
import com.example.shoplive_problem.data.local.CharacterDatabase
import com.example.shoplive_problem.data.network.RetrofitService
import com.example.shoplive_problem.data.repository.FavoriteRepositoryImpl
import com.example.shoplive_problem.data.repository.MarvelRepositoryImpl
import com.example.shoplive_problem.domain.repository.FavoriteRepository
import com.example.shoplive_problem.domain.repository.MarvelRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

private val apiModule = module {
    single { RetrofitService() }
    single { get<RetrofitService>().retrofit.create(MarvelApi::class.java) }
}

private val repositoryModule = module {
    single<MarvelRepository> { MarvelRepositoryImpl(get()) }
    single<FavoriteRepository> { FavoriteRepositoryImpl(get()) }
}

private val dataSourceModule = module {
    single<MarvelRemoteDataSource> { MarvelRemoteDataSourceImpl(get()) }
    single<CharacterLocalDataSource> { CharacterLocalDataSourceImpl(get()) }
    single { CharacterDatabase.getInstance(androidContext()) }
}

val dataModule = apiModule + repositoryModule + dataSourceModule