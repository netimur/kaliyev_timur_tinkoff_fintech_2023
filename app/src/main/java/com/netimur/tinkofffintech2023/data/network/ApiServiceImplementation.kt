package com.netimur.tinkofffintech2023.data.network

import com.netimur.tinkofffintech2023.BuildConfig
import com.netimur.tinkofffintech2023.common.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiServiceImplementation {
    private val retrofit: Retrofit = Retrofit.Builder()
        .client(getHttpClient())
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val service: ApiService = retrofit.create(ApiService::class.java)


    private fun getHttpClient(): OkHttpClient {
        val httpLoggingInterceptor: HttpLoggingInterceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }
}