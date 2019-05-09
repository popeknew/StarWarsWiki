package com.example.nbainfoapp.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.nbainfoapp.R
import com.example.nbainfoapp.adapter.FilmsRecyclerViewAdapter
import com.example.nbainfoapp.repository.FilmsDatabaseRepository
import kotlinx.android.synthetic.main.fragment_films_favorites.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.support.kodein
import org.kodein.di.generic.instance

class FilmsFavoritesFragment : Fragment(), KodeinAware {

    override val kodein by kodein()
    private val filmsDatabaseRepository by instance<FilmsDatabaseRepository>()
    private val filmsRecyclerViewAdapter = FilmsRecyclerViewAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_films_favorites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        recycler_view.adapter = filmsRecyclerViewAdapter
        synchronizeFilmsDatabase()
    }

    private fun synchronizeFilmsDatabase() = GlobalScope.launch(Dispatchers.Main) {
        val list = filmsDatabaseRepository.getFavoriteFilms()
        filmsRecyclerViewAdapter.swapFilms(list)
    }
}
