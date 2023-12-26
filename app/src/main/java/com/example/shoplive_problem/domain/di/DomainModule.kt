package com.example.shoplive_problem.domain.di

import com.example.shoplive_problem.domain.usecase.FavoriteUseCase
import com.example.shoplive_problem.domain.usecase.GetCharacterListUseCase
import org.koin.dsl.module

private val useCaseModule = module {
    single { GetCharacterListUseCase(get()) }
    single { FavoriteUseCase(get()) }
}

val domainModule = useCaseModule