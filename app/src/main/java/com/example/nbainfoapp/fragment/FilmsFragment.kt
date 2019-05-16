package com.example.nbainfoapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import com.example.nbainfoapp.R
import com.example.nbainfoapp.activity.FavoritesActivity
import com.example.nbainfoapp.activity.FilmsDetailsActivity
import com.example.nbainfoapp.activity.NavigationActivity
import com.example.nbainfoapp.adapter.FilmsRecyclerViewAdapter
import com.example.nbainfoapp.model.Film
import com.example.nbainfoapp.repository.RepositoryRetrofit
import kotlinx.android.synthetic.main.fragment_films.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.kodein.di.KodeinAware
import org.kodein.di.android.support.kodein
import org.kodein.di.generic.instance

class FilmsFragment : Fragment(), KodeinAware {

    override val kodein by kodein()
    private val repositoryRetrofit: RepositoryRetrofit by instance()
    private val filmsRecyclerViewAdapter = FilmsRecyclerViewAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_films, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        recycler_view.scheduleLayoutAnimation()
        recycler_view.adapter = filmsRecyclerViewAdapter
        getFilmsFromServer(repositoryRetrofit)
        filmsRecyclerViewAdapter.onRowClickListener = { filmModel, image ->
            startDetailsActivity(filmModel, image)
        }
    }

    private fun getFilmsFromServer(repositoryRetrofit: RepositoryRetrofit) {
        GlobalScope.launch {
            withContext(Dispatchers.Main) {
                loading_spinner.visibility = View.VISIBLE
                val list = repositoryRetrofit.getFilms()
                createListOfFilms(list)
                loading_spinner.visibility = View.GONE
            }
        }
    }

    private fun createListOfFilms(list: MutableList<Film>) {
        recycler_view.scheduleLayoutAnimation()
        filmsRecyclerViewAdapter.swapFilms(list)
    }

    private fun startDetailsActivity(film: Film, image: View) {
        val intent = FilmsDetailsActivity.getIntent(context!!, film)
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity as NavigationActivity, image, "sendImage")
        startActivity(intent, options.toBundle())
    }
}
