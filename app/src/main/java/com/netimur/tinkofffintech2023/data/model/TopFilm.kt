package com.netimur.tinkofffintech2023.data.model

data class TopFilm(
    val filmId: Int,
    val nameRu: String,
    val nameEn: String,
    val year: String,
    val filmLength: String,
    val countries: List<Country>,
    val genres: List<Genre>,
    val rating: String,
    val ratingVoteCount: Int,
    val posterUrl: String,
    val posterUrlPreview: String,
    val ratingChange: Any
) {
}