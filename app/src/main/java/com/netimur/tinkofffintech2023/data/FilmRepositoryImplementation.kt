package com.netimur.tinkofffintech2023.data

import android.content.Context
import com.netimur.tinkofffintech2023.common.InternetConnection
import com.netimur.tinkofffintech2023.data.database.core.AppDatabase
import com.netimur.tinkofffintech2023.data.database.core.DatabaseInstance
import com.netimur.tinkofffintech2023.data.model.*
import com.netimur.tinkofffintech2023.data.network.ApiService
import com.netimur.tinkofffintech2023.data.network.ApiServiceImplementation

import java.util.LinkedList

class FilmRepositoryImplementation(context: Context) : FilmRepository {
    private val apiService: ApiService = ApiServiceImplementation.getService(context)
    private val database: AppDatabase = DatabaseInstance.getDb(context)
    private val internetConnection: InternetConnection = InternetConnection(context)

    override suspend fun getFavouriteFilms(): List<FilmCardRepresentation> {
        return database.filmCardRepresentationDao().getFilmCardRepresentations()
    }

    override suspend fun addFavouriteFilm(film: FilmCardRepresentation) {
        database.filmCardRepresentationDao().addFilmCardRepresentation(film)
    }

    override suspend fun addFavouriteFilms(favouriteFilmsList: List<FilmCardRepresentation>) {
        database.filmCardRepresentationDao().addFilmCardRepresentations(favouriteFilmsList)
    }

    override suspend fun getTopFilms(): List<TopFilm> {
        return if (internetConnection.isAvailable()) {
            try {
                val response = apiService.getTopFilms()
                if (response.isSuccessful) {
                    response.body()?.films ?: LinkedList<TopFilm>()
                } else {
                    LinkedList<TopFilm>()
                }
            } catch (e: Exception) {
                LinkedList<TopFilm>()
            }
        } else {
            LinkedList<TopFilm>()
        }
    }

    override suspend fun getFilmDetailsById(id: Int): FilmResponseBody {
        return if (internetConnection.isAvailable()) {
            try {
                val response = apiService.getFilmDetails(id)
                if (response.isSuccessful) {
                    response.body() ?: EmptyFilmResponseBody()
                } else {
                    EmptyFilmResponseBody()
                }
            } catch (e: Exception) {
                EmptyFilmResponseBody()
            }
        } else {
            EmptyFilmResponseBody()
        }
    }

    override suspend fun deleteFavouriteFilm(film: FilmCardRepresentation) {
        database.filmCardRepresentationDao().deleteFilmCardRepresentation(film)
    }
}