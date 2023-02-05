package com.netimur.tinkofffintech2023.ui.mainpage

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.netimur.tinkofffintech2023.data.FilmRepository
import com.netimur.tinkofffintech2023.data.FilmRepositoryImplementation
import com.netimur.tinkofffintech2023.data.model.FilmCardRepresentation
import com.netimur.tinkofffintech2023.databinding.FilmItemBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FavouriteFilmsRecyclerViewAdapter(
    private val films: List<FilmCardRepresentation>
) :
    RecyclerView.Adapter<FavouriteFilmsRecyclerViewAdapter.FavouriteFilmCardViewHolder>(),
    RemoveCallback {
    private val favouriteFilms: MutableList<FilmCardRepresentation> = films.toMutableList()

    override fun onRemove(film: FilmCardRepresentation, position: Int) {
        favouriteFilms.remove(film)
        notifyItemRemoved(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteFilmCardViewHolder {
        val context: Context = parent.context
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: FilmItemBinding = FilmItemBinding.inflate(layoutInflater, parent, false)
        return FavouriteFilmCardViewHolder(binding, context, this)
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
        private val context: Context,
        private val removeCallback: RemoveCallback
    ) :
        ViewHolder(binding.root) {
        private lateinit var filmCardRepresentation: FilmCardRepresentation
        private val repository: FilmRepository = FilmRepositoryImplementation(context)

        @OptIn(DelicateCoroutinesApi::class)
        fun bindData(filmCardRepresentation: FilmCardRepresentation) {
            this.filmCardRepresentation = filmCardRepresentation
            binding.apply {
                filmName.text = filmCardRepresentation.name
                filmGenre.text = filmCardRepresentation.genre
                filmYear.text = "(${filmCardRepresentation.year})"
                favouriteStar.visibility = View.VISIBLE
                root.apply {
                    setOnLongClickListener(View.OnLongClickListener {
                        GlobalScope.launch(Dispatchers.IO) {
                            repository.deleteFavouriteFilm(filmCardRepresentation)
                        }
                        removeCallback.onRemove(filmCardRepresentation, adapterPosition)
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