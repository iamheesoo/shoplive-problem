package com.example.shoplive_problem.data.network

sealed class ResultData<T>(
    val data: T? = null,
    val message: String? = null
) {
    data class Success<T>(
        val successData: T
    ) : ResultData<T>(successData)

    data class Error<T>(
        val errorData: T? = null,
        val errorMessage: String? = null
    ) : ResultData<T>(errorData, errorMessage)
}