package com.example.shoplive_problem.domain.di

import com.example.shoplive_problem.domain.usecase.AddFavoriteListUseCase
import com.example.shoplive_problem.domain.usecase.DeleteFavoriteListUseCase
import com.example.shoplive_problem.domain.usecase.GetCharacterListUseCase
import com.example.shoplive_problem.domain.usecase.GetFavoriteListUseCase
import org.koin.dsl.module

private val useCaseModule = module {
    single { GetCharacterListUseCase(get()) }
    single { AddFavoriteListUseCase(get()) }
    single { DeleteFavoriteListUseCase(get()) }
    single { GetFavoriteListUseCase(get()) }
}

val domainModule = useCaseModule