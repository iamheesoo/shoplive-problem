package com.example.shoplive_problem.domain.base

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

abstract class BaseUseCase<in P, R>() {
    suspend operator fun invoke(params: P): Flow<R> {
        return flow {
            emit(
                execute(params)
            )
        }
    }

    protected abstract suspend fun execute(params: P): R
}