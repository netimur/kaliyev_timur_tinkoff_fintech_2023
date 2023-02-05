package com.netimur.tinkofffintech2023.ui.mainpage


import android.content.Context

import androidx.lifecycle.*
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import com.netimur.tinkofffintech2023.common.mapToFilmCardRepresentation
import com.netimur.tinkofffintech2023.data.FilmRepository
import com.netimur.tinkofffintech2023.data.FilmRepositoryImplementation
import com.netimur.tinkofffintech2023.data.model.FilmCardRepresentation

import com.netimur.tinkofffintech2023.data.model.TopFilm

import kotlinx.coroutines.*
import java.util.LinkedList


class TopFilmsViewModel(private val context: Context) : ViewModel() {
    private val filmRepository: FilmRepository = FilmRepositoryImplementation(context)
    val topFilmsCardRepresentations: MutableLiveData<List<FilmCardRepresentation>> =
        MutableLiveData()
    val favouriteFilms: MutableLiveData<MutableList<FilmCardRepresentation>> = MutableLiveData()

    init {
        getTopFilms()
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun getTopFilms() {
        if (topFilmsCardRepresentations.value?.isNotEmpty() == true) {
            topFilmsCardRepresentations.value = topFilmsCardRepresentations.value
        } else {
            GlobalScope.launch(Dispatchers.IO) {
                val topFilmsFromNetwork: List<TopFilm> = filmRepository.getTopFilms()

                if (topFilmsFromNetwork.isEmpty()) {
                    topFilmsCardRepresentations.postValue(LinkedList())
                } else {
                    val repositoryFilmsList = filmRepository.getFavouriteFilms()
                    val resultList: List<FilmCardRepresentation> =
                        mergeFilms(topFilmsFromNetwork, repositoryFilmsList)
                    topFilmsCardRepresentations.postValue(resultList)
                }
            }
        }
    }

    private fun mergeFilms(
        filmsFromNetwork: List<TopFilm>,
        favouriteFilms: List<FilmCardRepresentation>
    ): List<FilmCardRepresentation> {
        val filmCardRepresentations: MutableList<FilmCardRepresentation> = ArrayList()
        val favouriteFilmsIds: MutableSet<Int> = HashSet()
        for (element in favouriteFilms) {
            favouriteFilmsIds.add(element.id)
        }

        for (i in filmsFromNetwork.indices) {
            val filmCard = filmsFromNetwork[i].mapToFilmCardRepresentation()
            if (favouriteFilmsIds.contains(filmsFromNetwork[i].filmId)) {
                filmCard.isFavourite = true
            }
            filmCardRepresentations.add(filmCard)
        }
        return filmCardRepresentations
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun getFavouriteFilms() {
        GlobalScope.launch(Dispatchers.IO) {
            val localFavouriteFilms: List<FilmCardRepresentation> =
                filmRepository.getFavouriteFilms()
            if (localFavouriteFilms.isEmpty()) {
                favouriteFilms.postValue(ArrayList())
            } else {
                favouriteFilms.postValue(localFavouriteFilms.toMutableList())
            }
        }
    }

    fun addFavouriteFilm(film: FilmCardRepresentation) {
        markFilmAsFavourite(film)
        GlobalScope.launch(Dispatchers.IO) {
            filmRepository.addFavouriteFilm(film)
        }
    }

    private fun markFilmAsFavourite(film: FilmCardRepresentation) {
        val favouriteFilmIndex: Int? = topFilmsCardRepresentations.value?.indexOf(film)
        topFilmsCardRepresentations.value?.get(favouriteFilmIndex!!)?.isFavourite = true
        addFavouriteFilmToFavouritesList(film)
    }

    private fun addFavouriteFilmToFavouritesList(film: FilmCardRepresentation) {
        favouriteFilms.value?.add(film)
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun deleteFavouriteFilm(film: FilmCardRepresentation) {
        deleteFavouriteFilmFromTopFilms(film)
        deleteFilmFromFavouriteFilms(film)
        GlobalScope.launch(Dispatchers.IO) {
            filmRepository.deleteFavouriteFilm(film)
        }
    }

    private fun deleteFavouriteFilmFromTopFilms(film: FilmCardRepresentation) {
        val topListFilmIndex: Int = topFilmsCardRepresentations.value!!.indexOf(film)
        topFilmsCardRepresentations.value!![topListFilmIndex].isFavourite = false
    }

    private fun deleteFilmFromFavouriteFilms(film: FilmCardRepresentation) {
        val favouriteListFilmIndex: Int? = favouriteFilms.value?.indexOf(film)
        val favouriteFilmsList: MutableList<FilmCardRepresentation> =
            LinkedList(favouriteFilms.value ?: ArrayList())
        if (favouriteListFilmIndex != null) {
            favouriteFilmsList.removeAt(favouriteListFilmIndex)
        }
        favouriteFilms.value = favouriteFilmsList
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val application = checkNotNull(extras[APPLICATION_KEY])
                return TopFilmsViewModel(application.applicationContext) as T
            }
        }
    }
}