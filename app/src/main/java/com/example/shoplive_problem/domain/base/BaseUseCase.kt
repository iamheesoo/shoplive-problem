package com.example.shoplive_problem.domain.base

abstract class BaseUseCase<in P, R> {
    suspend operator fun invoke(params: P): R {
        return execute(params)
    }

    protected abstract suspend fun execute(params: P): R
}