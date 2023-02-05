package com.netimur.tinkofffintech2023.ui.mainpage

import android.view.LayoutInflater
import android.view.View
import android.view.View.OnLongClickListener
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.netimur.tinkofffintech2023.data.model.FilmCardRepresentation
import com.netimur.tinkofffintech2023.databinding.FilmItemBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.*

class FilmsRecyclerViewAdapter(
    private var filmCardRepresentations: List<FilmCardRepresentation>,
    private val viewModel: TopFilmsViewModel
) :
    RecyclerView.Adapter<FilmsRecyclerViewAdapter.FilmViewHolder>() {
    private val fullList: List<FilmCardRepresentation> = filmCardRepresentations.toList()
    fun search(text: String) {
        updateList(fullList)
        val filteredList = filmCardRepresentations.filter { item ->
            item.name.contains(text, ignoreCase = true)
        }
        updateList(filteredList)
    }

    private fun updateList(filmCardRepresentations: List<FilmCardRepresentation>) {
        this.filmCardRepresentations = filmCardRepresentations
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = FilmItemBinding.inflate(layoutInflater, parent, false)
        return FilmViewHolder(binding, viewModel)
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
        private val viewModel: TopFilmsViewModel
    ) :
        RecyclerView.ViewHolder(binding.root) {
        private lateinit var filmCardRepresentation: FilmCardRepresentation

        @OptIn(DelicateCoroutinesApi::class)
        fun bindData(filmCardRepresentation: FilmCardRepresentation) {
            this.filmCardRepresentation = filmCardRepresentation
            binding.apply {
                filmName.text = filmCardRepresentation.name
                filmGenre.text = filmCardRepresentation.genre
                filmYear.text = "(${filmCardRepresentation.year})"
                root.apply {
                    setOnClickListener {
                        try {
                            val action =
                                MainPageFragmentDirections.actionMainPageFragmentToFilmDetailsFragment(
                                    filmCardRepresentation.id
                                )
                            Navigation.findNavController(binding.root).navigate(action)
                        } catch (e: java.lang.Exception) {
                            e.printStackTrace()
                        }
                    }
                    setOnLongClickListener(OnLongClickListener {
                        viewModel.addFavouriteFilm(filmCardRepresentation)
                        binding.favouriteStar.visibility = View.VISIBLE
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