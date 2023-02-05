package com.netimur.tinkofffintech2023.ui.filmdetails

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.netimur.tinkofffintech2023.data.model.ShortFilmDetails
import com.netimur.tinkofffintech2023.common.mapToShortFilmDetails
import com.netimur.tinkofffintech2023.data.FilmRepository
import com.netimur.tinkofffintech2023.data.FilmRepositoryImplementation
import com.netimur.tinkofffintech2023.data.model.EmptyFilmResponseBody
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FilmDetailsViewModel(private val context: Context) : ViewModel() {
    val shortFilmDetails: MutableLiveData<ShortFilmDetails> = MutableLiveData()
    private val filmRepository: FilmRepository = FilmRepositoryImplementation(context)

    @OptIn(DelicateCoroutinesApi::class)
    fun loadFilmData(filmId: Int) {
        GlobalScope.launch(Dispatchers.IO) {
            val result = filmRepository.getFilmDetailsById(filmId)
            if (result is EmptyFilmResponseBody) {
                shortFilmDetails.postValue(EmptyFilmDetails())
            } else {
                shortFilmDetails.postValue(result.mapToShortFilmDetails())
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val application =
                    checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY])
                return FilmDetailsViewModel(application.applicationContext) as T
            }
        }
    }
}