package com.netimur.tinkofffintech2023.ui.filmdetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.netimur.tinkofffintech2023.data.model.ShortFilmDetails
import com.netimur.tinkofffintech2023.databinding.FragmentFilmDetailsBinding
import com.squareup.picasso.Picasso

class FilmDetailsFragment : Fragment() {
    private var binding: FragmentFilmDetailsBinding? = null
    private val viewModel: FilmDetailsViewModel by viewModels { FilmDetailsViewModel.Factory }
    private val args: FilmDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFilmDetailsBinding.inflate(inflater, container, false)
        val view: View = binding!!.root
        setViewModelObserver()
        val filmId: Int = args.filmId
        viewModel.loadFilmData(filmId)
        loadDetails()
        binding!!.apply {
            backButton.setOnClickListener {
                Navigation.findNavController(view).popBackStack()
            }
            retryButton.setOnClickListener {
                loadDetails()
            }
        }
        return view
    }

    private fun setViewModelObserver() {
        viewModel.shortFilmDetails.observe(viewLifecycleOwner) {
            if (it is EmptyFilmDetails) {
                emptyDetails()
            } else {
                showDetails(it)
            }
        }
    }

    private fun emptyDetails() {
        binding!!.apply {
            filmName.visibility = View.INVISIBLE
            filmPoster.visibility = View.INVISIBLE
            filmDescription.visibility = View.INVISIBLE
            filmCountries.visibility = View.INVISIBLE
            filmGenre.visibility = View.INVISIBLE
            internetConnectionErrorImage.visibility = View.VISIBLE
            internetConnectionErrorMessage.visibility = View.VISIBLE
            retryButton.visibility = View.VISIBLE
            progressBar.visibility = View.INVISIBLE
            genreLabel.visibility = View.INVISIBLE
            countriesLabel.visibility = View.INVISIBLE
        }
    }

    private fun showDetails(filmDetails: ShortFilmDetails) {
        binding!!.apply {
            filmName.apply {
                text = filmDetails.filmName
                visibility = View.VISIBLE
            }
            filmDescription.apply {
                text = filmDetails.description
                visibility = View.VISIBLE
            }
            filmCountries.apply {
                text = filmDetails.filmCountries
                visibility = View.VISIBLE
            }
            filmGenre.apply {
                text = filmDetails.filmGenre
                visibility = View.VISIBLE
            }
            filmPoster.visibility = View.VISIBLE
            Picasso.get().load(filmDetails.posterUrl).fit().into(binding!!.filmPoster)
            internetConnectionErrorImage.visibility = View.INVISIBLE
            internetConnectionErrorMessage.visibility = View.INVISIBLE
            retryButton.visibility = View.INVISIBLE
            progressBar.visibility = View.INVISIBLE
            genreLabel.visibility = View.VISIBLE
            countriesLabel.visibility = View.VISIBLE
        }

    }

    private fun loadDetails() {
        binding!!.apply {
            filmName.visibility = View.INVISIBLE
            filmPoster.visibility = View.INVISIBLE
            filmDescription.visibility = View.INVISIBLE
            filmCountries.visibility = View.INVISIBLE
            filmGenre.visibility = View.INVISIBLE
            internetConnectionErrorImage.visibility = View.INVISIBLE
            internetConnectionErrorImage.visibility = View.INVISIBLE
            retryButton.visibility = View.INVISIBLE
            progressBar.visibility = View.VISIBLE
            genreLabel.visibility = View.INVISIBLE
            countriesLabel.visibility = View.INVISIBLE
        }
        viewModel.loadFilmData(args.filmId)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}