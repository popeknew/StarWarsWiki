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
import com.example.nbainfoapp.adapter.FilmsRecyclerViewAdapter
import com.example.nbainfoapp.model.Film
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
    private val deleteFromFavoritesDialogFragment = DeleteFromFavoritesDialogFragment()

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
        recycler_view.scheduleLayoutAnimation()
        recycler_view.adapter = filmsRecyclerViewAdapter
        synchronizeFilmsDatabase()
        filmsRecyclerViewAdapter.onRowLongClickListener = { film, position ->
            deleteFromFavoritesDialogFragment.show(fragmentManager!!, "tag")
            deleteFromFavoritesDialogFragment.deleteDecision = { decision ->
                deleteFilmFromDatabase(film, position, decision)
                synchronizeFilmsDatabase()
            }
        }
        filmsRecyclerViewAdapter.onRowClickListener = { film, image ->
            startDetailsActivity(film, image)
        }
    }

    private fun synchronizeFilmsDatabase() = GlobalScope.launch(Dispatchers.Main) {
        val list = filmsDatabaseRepository.getFavoriteFilms()
        recycler_view.scheduleLayoutAnimation()
        filmsRecyclerViewAdapter.swapFilms(list)
    }

    private fun deleteFilmFromDatabase(film: Film, position: Int, decision: Boolean) {
        if (decision) {
            GlobalScope.launch(Dispatchers.Main) {
                filmsDatabaseRepository.deleteFilm(film)
                filmsRecyclerViewAdapter.removePerson(film, position)
            }
        }
    }

    private fun startDetailsActivity(film: Film, image: View) {
        val intent = FilmsDetailsActivity.getIntent(context!!, film)
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
            activity as FavoritesActivity,
            image,
            "sendImage"
        )
        startActivity(intent, options.toBundle())
    }
}
