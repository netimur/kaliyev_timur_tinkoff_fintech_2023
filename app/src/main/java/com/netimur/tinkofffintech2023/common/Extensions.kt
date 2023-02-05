package com.netimur.tinkofffintech2023.common

import com.netimur.tinkofffintech2023.data.model.*

fun List<Genre>.mapGenresToStringList(): List<String> {
    val genresList: MutableList<String> = ArrayList()
    for (genre in this) {
        genresList.add(genre.genre)
    }
    return genresList
}

fun List<Country>.mapCountriesToStringList(): List<String> {
    val countriesList: MutableList<String> = ArrayList()
    for (country in this) {
        countriesList.add(country.country)
    }
    return countriesList
}

fun List<String>.mapToString(): String {
    var result: String = String()
    val itemCount = this.size
    if (itemCount > 0) {
        for (item in 1 until itemCount - 1) {
            result += item
        }
        result += this[this.lastIndex]
    }
    return result
}

fun TopFilm.mapToFilmCardRepresentation(): FilmCardRepresentation {
    return FilmCardRepresentation(
        filmId,
        nameRu,
        genres.mapGenresToStringList().mapToString(),
        year,
        posterUrlPreview,
        false
    )
}

fun FilmResponseBody.mapToShortFilmDetails(): ShortFilmDetails {
    return ShortFilmDetails(
        this.nameRu,
        this.genres.mapGenresToStringList().mapToString(),
        this.countries.mapCountriesToStringList().mapToString(),
        this.description,
        this.posterUrl
    )
}