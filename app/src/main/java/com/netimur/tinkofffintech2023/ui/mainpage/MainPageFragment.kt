package com.netimur.tinkofffintech2023.ui.mainpage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
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
            searchButton.setOnClickListener {
                searchFilms()
            }
            backButton.setOnClickListener {
                if (this.pageHeader.text == "Популярное") {
                    requestFilms()
                    viewModel.getTopFilms()
                } else {
                    viewModel.getFavouriteFilms()
                }
            }
        }

        viewModel.apply {
            favouriteFilms.observe(viewLifecycleOwner) {
                if (it.isEmpty()) {
                    emptyFavourites()
                } else {
                    val filmsRecyclerViewAdapter = FavouriteFilmsRecyclerViewAdapter(it, viewModel)
                    binding?.filmsRecyclerView?.adapter = filmsRecyclerViewAdapter
                    showFavourites()
                }
            }
            topFilmsCardRepresentations.observe(viewLifecycleOwner) {
                if (it.isEmpty()) {
                    emptyPopular()
                } else {
                    val filmsRecyclerViewAdapter = FilmsRecyclerViewAdapter(it, viewModel)
                    binding?.filmsRecyclerView?.adapter = filmsRecyclerViewAdapter
                    showPopular()
                }
            }
        }

        binding!!.searchBar.editText?.doOnTextChanged { text, _, _, _ ->
            search(text.toString())
        }
        return view
    }

    private fun search(searchingParameter: String) {
        val recyclerViewAdapter = binding!!.filmsRecyclerView.adapter
        if (recyclerViewAdapter is FilmsRecyclerViewAdapter) {
            recyclerViewAdapter.search(searchingParameter.toString())
        } else if (recyclerViewAdapter is FavouriteFilmsRecyclerViewAdapter) {
            recyclerViewAdapter.search(searchingParameter.toString())
        }

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
            pageHeader.visibility = View.VISIBLE
            searchButton.visibility = View.VISIBLE
            searchBar.visibility = View.INVISIBLE
            backButton.visibility = View.INVISIBLE
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
            pageHeader.visibility = View.VISIBLE
            favouritesButton.visibility = View.VISIBLE
            searchButton.visibility = View.VISIBLE
            searchBar.visibility = View.INVISIBLE
            backButton.visibility = View.INVISIBLE
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
            pageHeader.visibility = View.VISIBLE
            searchButton.visibility = View.VISIBLE
            searchBar.visibility = View.INVISIBLE
            backButton.visibility = View.INVISIBLE
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
            pageHeader.visibility = View.VISIBLE
            searchButton.visibility = View.VISIBLE
            searchBar.visibility = View.INVISIBLE
            backButton.visibility = View.INVISIBLE
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
            filmsRecyclerView.visibility = View.INVISIBLE
            progressBar.visibility = View.VISIBLE
            pageHeader.visibility = View.VISIBLE
            searchButton.visibility = View.VISIBLE
            searchBar.visibility = View.INVISIBLE
            backButton.visibility = View.INVISIBLE
        }
        viewModel.getTopFilms()
    }

    private fun searchFilms() {
        binding!!.apply {
            internetConnectionErrorImage.visibility = View.INVISIBLE
            internetConnectionErrorMessage.visibility = View.INVISIBLE
            retryButton.visibility = View.INVISIBLE
            popularButton.visibility = View.INVISIBLE
            noFavouriteImage.visibility = View.INVISIBLE
            noFavouriteMessage.visibility = View.INVISIBLE
            favouritesButton.visibility = View.INVISIBLE
            progressBar.visibility = View.INVISIBLE
            searchButton.visibility = View.INVISIBLE
            pageHeader.visibility = View.INVISIBLE
            searchBar.visibility = View.VISIBLE
            filmsRecyclerView.visibility = View.VISIBLE
            backButton.visibility = View.VISIBLE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}