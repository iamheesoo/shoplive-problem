package com.example.shoplive_problem.domain.base

import com.example.shoplive_problem.data.network.ResultData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

abstract class BaseUseCase<in P, R>() {
    suspend operator fun invoke(params: P): Flow<ResultData<R>> {
        return flow {
            emit(
                try {
                    execute(params)
                } catch (e: Exception) {
                    ResultData.Error(errorMessage = e.toString())
                }
            )
        }
    }

    protected abstract suspend fun execute(params: P): ResultData<R>
}