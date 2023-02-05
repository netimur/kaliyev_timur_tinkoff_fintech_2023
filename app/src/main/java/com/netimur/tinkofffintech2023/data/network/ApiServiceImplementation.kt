package com.netimur.tinkofffintech2023.data.network

import android.content.Context
import com.netimur.tinkofffintech2023.common.Constants
import com.netimur.tinkofffintech2023.common.InternetConnection
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiServiceImplementation {

    private lateinit var service: ApiService

    fun getService(context: Context): ApiService {

        if (!this::service.isInitialized) {
            val client: OkHttpClient = getHttpClient(context)
            val retrofit: Retrofit = Retrofit.Builder()
                .client(client)
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).build()
            service = retrofit.create(ApiService::class.java)
        }
        return service
    }


    private fun getHttpClient(context: Context): OkHttpClient {
        val cacheSize: Long = 5 * 1024 * 1024
        val cache: Cache = Cache(context.cacheDir, cacheSize)

        return OkHttpClient.Builder()
            .cache(cache)
            .addInterceptor { chain ->
                val internetConnection: InternetConnection = InternetConnection(context)
                var request = chain.request()

                request = if (internetConnection.isAvailable()) {
                    request.newBuilder().header("Cache-Control", "public, max-age=" + 5).build()
                } else {
                    request.newBuilder().header(
                        "Cache-Control",
                        "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7
                    ).build()
                }
                chain.proceed(request)
            }
            .build()
    }
}