package com.netimur.tinkofffintech2023.ui.mainpage

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnLongClickListener
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.netimur.tinkofffintech2023.data.FilmRepository
import com.netimur.tinkofffintech2023.data.FilmRepositoryImplementation
import com.netimur.tinkofffintech2023.data.model.FilmCardRepresentation
import com.netimur.tinkofffintech2023.databinding.FilmItemBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.*

class FilmsRecyclerViewAdapter(private val filmCardRepresentations: List<FilmCardRepresentation>) :
    RecyclerView.Adapter<FilmsRecyclerViewAdapter.FilmViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = FilmItemBinding.inflate(layoutInflater, parent, false)
        return FilmViewHolder(binding, parent.context)
    }

    override fun getItemCount(): Int {
        return filmCardRepresentations.size
    }

    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {
        val currentFilmCardRepresentation: FilmCardRepresentation =
            filmCardRepresentations[position]
        holder.bindData(currentFilmCardRepresentation)
    }

    class FilmViewHolder(
        private val binding: FilmItemBinding,
        private val context: Context
    ) :
        RecyclerView.ViewHolder(binding.root) {
        private lateinit var filmCardRepresentation: FilmCardRepresentation
        private val repository: FilmRepository = FilmRepositoryImplementation(context)

        @OptIn(DelicateCoroutinesApi::class)
        fun bindData(filmCardRepresentation: FilmCardRepresentation) {
            this.filmCardRepresentation = filmCardRepresentation
            binding.apply {
                filmName.text = filmCardRepresentation.name
                filmGenre.text = filmCardRepresentation.genre
                filmYear.text = "(${filmCardRepresentation.year})"
                root.apply {
                    setOnClickListener(View.OnClickListener {
                        val action =
                            MainPageFragmentDirections.actionMainPageFragmentToFilmDetailsFragment(
                                filmCardRepresentation.id
                            )
                        Navigation.findNavController(binding.root).navigate(action)
                    })
                    setOnLongClickListener(OnLongClickListener {
                        GlobalScope.launch(Dispatchers.IO) {
                            repository.addFavouriteFilm(filmCardRepresentation)
                        }
                        filmCardRepresentation.isFavourite = true
                        binding.favouriteStar.visibility = View.VISIBLE
                        val snackbar = Snackbar.make(
                            binding.root,
                            "Фильм добавлен в избранное",
                            Snackbar.LENGTH_SHORT
                        )
                        snackbar.setAction("Отменить", View.OnClickListener {
                            GlobalScope.launch(Dispatchers.IO) {
                                repository.deleteFavouriteFilm(filmCardRepresentation)
                            }
                            filmCardRepresentation.isFavourite = false
                            binding.favouriteStar.visibility = View.INVISIBLE
                        })
                        snackbar.show()

                        return@OnLongClickListener true
                    })
                }
            }

            if (filmCardRepresentation.isFavourite) {
                binding.favouriteStar.visibility = View.VISIBLE
            } else {
                binding.favouriteStar.visibility = View.INVISIBLE
            }
            loadFilmPreview()
        }

        private fun loadFilmPreview() {
            Picasso.get().load(filmCardRepresentation.posterUrlPreview).into(binding.filmPoster)
        }
    }
}