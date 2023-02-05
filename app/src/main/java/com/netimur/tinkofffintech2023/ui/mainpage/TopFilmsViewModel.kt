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
    val filmsList: MutableLiveData<List<TopFilm>> = MutableLiveData()
    val topFilmsCardRepresentations: MutableLiveData<List<FilmCardRepresentation>> =
        MutableLiveData()
    val favouriteFilms: MutableLiveData<List<FilmCardRepresentation>> = MutableLiveData()

    init {
        filmsList.value = ArrayList()
        getTopFilms()
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun getTopFilms() {
        GlobalScope.launch(Dispatchers.IO) {
            val topFilms: MutableMap<Int, TopFilm> = HashMap()
            val topFilmsFromNetwork: List<TopFilm> = filmRepository.getTopFilms()
            if (topFilmsFromNetwork.isEmpty()) {
                topFilmsCardRepresentations.postValue(LinkedList())
            } else {
                for (film in topFilmsFromNetwork) {
                    topFilms[film.filmId] = film
                }
                val repositoryFilms: MutableMap<Int, FilmCardRepresentation> = HashMap()
                val repositoryFilmsList = filmRepository.getFavouriteFilms()
                for (film in repositoryFilmsList) {
                    repositoryFilms[film.id] = film
                }
                val resultTopList: MutableList<FilmCardRepresentation> = ArrayList()
                for (entry in topFilms.entries.iterator()) {
                    val card: FilmCardRepresentation =
                        entry.value.mapToFilmCardRepresentation()
                    if (repositoryFilms.containsKey(entry.key)) {
                        card.isFavourite = true
                    }
                    resultTopList.add(card)
                    topFilmsCardRepresentations.postValue(resultTopList)
                }
            }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun getFavouriteFilms() {
        GlobalScope.launch(Dispatchers.IO) {
            val localFavouriteFilms: List<FilmCardRepresentation> =
                filmRepository.getFavouriteFilms()
            if (localFavouriteFilms.isEmpty()) {
                favouriteFilms.postValue(ArrayList())
            } else {
                favouriteFilms.postValue(localFavouriteFilms)
            }
        }
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