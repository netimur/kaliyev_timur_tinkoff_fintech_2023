package com.netimur.tinkofffintech2023.data.network

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ResponseHandler<T> constructor(private val callback: ServerCallback) : Callback<T> {

    override fun onResponse(call: Call<T>, response: Response<T>) {
        if (response.isSuccessful) {
            callback.onSuccess(response.body())
        }
    }

    override fun onFailure(call: Call<T>, t: Throwable) {
        callback.onError()
    }
}