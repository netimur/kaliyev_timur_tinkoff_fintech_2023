package com.netimur.tinkofffintech2023.ui.mainpage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

import com.netimur.tinkofffintech2023.data.model.FilmCardRepresentation
import com.netimur.tinkofffintech2023.databinding.FilmItemBinding
import com.squareup.picasso.Picasso


class FavouriteFilmsRecyclerViewAdapter(
    private val films: List<FilmCardRepresentation>, private val viewModel: TopFilmsViewModel
) :
    RecyclerView.Adapter<FavouriteFilmsRecyclerViewAdapter.FavouriteFilmCardViewHolder>() {
    private var favouriteFilms: MutableList<FilmCardRepresentation> = films.toMutableList()
    private var tempFilms: MutableList<FilmCardRepresentation> = favouriteFilms


    fun search(text: String) {
        updateList(tempFilms)
        val filteredList = favouriteFilms.filter { item ->
            item.name.contains(text, ignoreCase = true)
        }
        updateList(filteredList)
    }

    private fun updateList(filmCardRepresentations: List<FilmCardRepresentation>) {
        this.favouriteFilms = filmCardRepresentations.toMutableList()
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteFilmCardViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: FilmItemBinding = FilmItemBinding.inflate(layoutInflater, parent, false)
        return FavouriteFilmCardViewHolder(binding, viewModel)
    }

    override fun getItemCount(): Int {
        return favouriteFilms.size
    }

    override fun onBindViewHolder(holder: FavouriteFilmCardViewHolder, position: Int) {
        val currentFilmCardRepresentation = favouriteFilms[position]
        holder.bindData(currentFilmCardRepresentation)
    }

    class FavouriteFilmCardViewHolder(
        private val binding: FilmItemBinding,
        private val viewModel: TopFilmsViewModel
    ) :
        ViewHolder(binding.root) {
        private lateinit var filmCardRepresentation: FilmCardRepresentation

        fun bindData(filmCardRepresentation: FilmCardRepresentation) {
            this.filmCardRepresentation = filmCardRepresentation
            binding.apply {
                filmName.text = filmCardRepresentation.name
                filmGenre.text = filmCardRepresentation.genre
                filmYear.text = "(${filmCardRepresentation.year})"
                favouriteStar.visibility = View.VISIBLE
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
                    setOnLongClickListener(View.OnLongClickListener {
                        viewModel.deleteFavouriteFilm(filmCardRepresentation)
                        return@OnLongClickListener true
                    })
                }
            }
            loadFilmPreview()
        }

        private fun loadFilmPreview() {
            Picasso.get().load(filmCardRepresentation.posterUrlPreview).into(binding.filmPoster)
        }
    }
}