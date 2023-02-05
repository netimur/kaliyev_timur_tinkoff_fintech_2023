package com.netimur.tinkofffintech2023.ui.mainpage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.netimur.tinkofffintech2023.databinding.FragmentMainPageBinding

class MainPageFragment : Fragment() {
    private var binding: FragmentMainPageBinding? = null
    private val viewModel: TopFilmsViewModel by viewModels { TopFilmsViewModel.Factory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainPageBinding.inflate(inflater, container, false)
        val view: View = binding!!.root
        binding?.apply {
            favouritesButton.setOnClickListener { viewModel.getFavouriteFilms() }
            popularButton.setOnClickListener {
                requestFilms()
                viewModel.getTopFilms()
            }
        }

        viewModel.apply {
            favouriteFilms.observe(viewLifecycleOwner) {
                if (it.isEmpty()) {
                    emptyFavourites()
                } else {
                    val filmsRecyclerViewAdapter = FavouriteFilmsRecyclerViewAdapter(it)
                    binding?.filmsRecyclerView?.adapter = filmsRecyclerViewAdapter
                    showFavourites()
                }
            }
            topFilmsCardRepresentations.observe(viewLifecycleOwner) {
                if (it.isEmpty()) {
                    emptyPopular()
                } else {
                    val filmsRecyclerViewAdapter = FilmsRecyclerViewAdapter(it)
                    binding?.filmsRecyclerView?.adapter = filmsRecyclerViewAdapter
                    showPopular()
                }
            }
        }
        return view
    }

    private fun emptyPopular() {
        binding!!.apply {
            progressBar.visibility = View.INVISIBLE
            internetConnectionErrorImage.visibility = View.VISIBLE
            internetConnectionErrorMessage.visibility = View.VISIBLE
            retryButton.visibility = View.VISIBLE
            popularButton.visibility = View.INVISIBLE
            favouritesButton.visibility = View.INVISIBLE
            pageHeader.text = "Популярное"
            filmsRecyclerView.visibility = View.INVISIBLE
            retryButton.setOnClickListener {
                requestFilms()
            }
        }
    }

    private fun showPopular() {
        binding?.apply {
            pageHeader.text = "Популярное"
            progressBar.visibility = View.INVISIBLE
            noFavouriteImage.visibility = View.INVISIBLE
            noFavouriteMessage.visibility = View.INVISIBLE
            internetConnectionErrorImage.visibility = View.INVISIBLE
            internetConnectionErrorMessage.visibility = View.INVISIBLE
            filmsRecyclerView.visibility = View.VISIBLE
            popularButton.visibility = View.VISIBLE
            favouritesButton.visibility = View.VISIBLE
        }
    }

    private fun emptyFavourites() {
        binding?.apply {
            progressBar.visibility = View.INVISIBLE
            noFavouriteImage.visibility = View.VISIBLE
            noFavouriteMessage.visibility = View.VISIBLE
            internetConnectionErrorImage.visibility = View.INVISIBLE
            internetConnectionErrorMessage.visibility = View.INVISIBLE
            filmsRecyclerView.visibility = View.INVISIBLE
            popularButton.visibility = View.VISIBLE
            favouritesButton.visibility = View.VISIBLE
        }
    }

    private fun showFavourites() {
        binding?.apply {
            pageHeader.text = "Избранное"
            progressBar.visibility = View.INVISIBLE
            noFavouriteImage.visibility = View.INVISIBLE
            noFavouriteMessage.visibility = View.INVISIBLE
            internetConnectionErrorImage.visibility = View.INVISIBLE
            internetConnectionErrorMessage.visibility = View.INVISIBLE
            filmsRecyclerView.visibility = View.VISIBLE
            popularButton.visibility = View.VISIBLE
            favouritesButton.visibility = View.VISIBLE
        }
    }

    private fun requestFilms() {
        binding!!.apply {
            internetConnectionErrorImage.visibility = View.INVISIBLE
            internetConnectionErrorMessage.visibility = View.INVISIBLE
            retryButton.visibility = View.INVISIBLE
            popularButton.visibility = View.INVISIBLE
            noFavouriteImage.visibility = View.INVISIBLE
            noFavouriteMessage.visibility = View.INVISIBLE
            favouritesButton.visibility = View.INVISIBLE
            progressBar.visibility = View.VISIBLE
            viewModel.getTopFilms()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}