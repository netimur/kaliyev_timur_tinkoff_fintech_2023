package com.netimur.tinkofffintech2023.data

import com.netimur.tinkofffintech2023.data.model.FilmCardRepresentation
import com.netimur.tinkofffintech2023.data.model.FilmResponseBody
import com.netimur.tinkofffintech2023.data.model.TopFilm

interface FilmRepository {
    suspend fun getFavouriteFilms(): List<FilmCardRepresentation>
    suspend fun addFavouriteFilm(film: FilmCardRepresentation)
    suspend fun addFavouriteFilms(favouriteFilmsList: List<FilmCardRepresentation>)
    suspend fun getTopFilms(): List<TopFilm>
    suspend fun getFilmDetailsById(id:Int): FilmResponseBody
    suspend fun deleteFavouriteFilm(film: FilmCardRepresentation)
}