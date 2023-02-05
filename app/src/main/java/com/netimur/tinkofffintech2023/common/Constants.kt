package com.netimur.tinkofffintech2023.common

class Constants {
    companion object {
        const val BASE_URL: String = "https://kinopoiskapiunofficial.tech/"
        const val TOP_ENDPOINT: String = "/api/v2.2/films/top"
        const val TOP_100_POPULAR_FILMS_QUERY_PARAM: String = "?type=TOP_100_POPULAR_FILMS"
        const val FILM_DESCRIPTION_ENDPOINT:String = " /api/v2.2/films/"
        const val API_KEY: String = "e30ffed0-76ab-4dd6-b41f-4c9da2b2735b"
        const val API_KEY_HEADER:String = "X-API-KEY: $API_KEY"
        const val CONTENT_TYPE_HEADER:String = "accept: application/json"
    }
}