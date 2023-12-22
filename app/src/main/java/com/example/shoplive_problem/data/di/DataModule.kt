package com.example.shoplive_problem.data.di

import com.example.shoplive_problem.data.api.MarvelApi
import com.example.shoplive_problem.data.network.RetrofitService
import com.example.shoplive_problem.data.repository.MarvelRepositoryImpl
import com.example.shoplive_problem.datasource.MarvelRemoteDataSource
import com.example.shoplive_problem.datasource.MarvelRemoteDataSourceImpl
import com.example.shoplive_problem.domain.repository.MarvelRepository
import org.koin.dsl.module

private val apiModule = module {
    single { RetrofitService() }
    single { get<RetrofitService>().retrofit.create(MarvelApi::class.java) }
}

private val repositoryModule = module {
    single<MarvelRepository> { MarvelRepositoryImpl(get()) }
}

private val dataSourceModule = module {
    single<MarvelRemoteDataSource> { MarvelRemoteDataSourceImpl(get()) }
}

val dataModule = apiModule + repositoryModule + dataSourceModule