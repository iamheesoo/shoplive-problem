package com.example.shoplive_problem.data.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface MarvelApi {
    @GET("v1/public/characters")
    suspend fun getCharacters(
        @QueryMap query: Map<String, String>
    ): Response<String>
}