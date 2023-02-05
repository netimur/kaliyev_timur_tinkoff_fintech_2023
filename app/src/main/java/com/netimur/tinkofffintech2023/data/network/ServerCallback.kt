package com.netimur.tinkofffintech2023.data.network

interface ServerCallback {
    fun <T> onSuccess(responseBody: T)
    fun onError()
}