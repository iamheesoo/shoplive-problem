package com.example.shoplive_problem.datasource

import com.example.shoplive_problem.BuildConfig
import com.example.shoplive_problem.data.api.MarvelApi
import com.example.shoplive_problem.data.network.ResultData
import com.example.shoplive_problem.data.network.apiCallSerialize
import com.example.shoplive_problem.data.repository.QueryConstant
import com.example.shoplive_problem.data.response.CharacterResponse
import com.example.shoplive_problem.data.utils.QueryUtils
import com.example.shoplive_problem.domain.usecase.GetCharacterListUseCase
import java.util.Date

class MarvelRemoteDataSourceImpl(private val marvelApi: MarvelApi): MarvelRemoteDataSource {
    override suspend fun getCharacterList(params: GetCharacterListUseCase.Params): ResultData<CharacterResponse> {
        val timestamp = Date().time
        val map = hashMapOf<String, String>().apply {
            put(QueryConstant.NAME_STARTS_WITH, params.text)
            put(QueryConstant.LIMIT, params.limit.toString())
            put(QueryConstant.OFFSET, params.offset.toString())
            put(QueryConstant.API_KEY, BuildConfig.API_PUBLIC_KEY)
            put(QueryConstant.TS, timestamp.toString())
            put(QueryConstant.HASH, QueryUtils.getMarvelApiHash(timestamp))
        }

        return apiCallSerialize {
            marvelApi.getCharacters(map)
        }
    }
}