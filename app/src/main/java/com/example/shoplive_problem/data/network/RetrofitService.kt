package com.example.shoplive_problem.data.network

import com.example.shoplive_problem.BuildConfig
import com.orhanobut.logger.Logger
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitService {
    private val client = OkHttpClient.Builder()
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(15, TimeUnit.SECONDS)
        .addInterceptor(
            HttpLoggingInterceptor(
                HttpLoggingInterceptor.Logger {
                    try {
                        JSONObject(it)
                        Logger.t("SHOPLIVE_LOGGER").json(it)
                    } catch (e: Exception) {
                        Logger.t("SHOPLIVE_LOGGER").i(it)
                    }
                }
            ).apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
        ).build()

    val retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.API_BASE_URL)
        .addConverterFactory(ScalarsConverterFactory.create())
        .client(client)
        .build()
}