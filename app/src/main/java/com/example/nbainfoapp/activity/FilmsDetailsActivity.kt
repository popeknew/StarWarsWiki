package com.example.nbainfoapp.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import com.example.nbainfoapp.R
import com.example.nbainfoapp.fragment.DeleteFromFavoritesDialogFragment
import com.example.nbainfoapp.model.Film
import com.example.nbainfoapp.model.Planet
import com.example.nbainfoapp.repository.FilmsDatabaseRepository
import kotlinx.android.synthetic.main.films_details.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class FilmsDetailsActivity : AppCompatActivity(), KodeinAware {

    override val kodein by kodein()
    private val filmsDatabaseRepository: FilmsDatabaseRepository by instance()
    private val deleteFromFavoritesDialogFragment = DeleteFromFavoritesDialogFragment()

    companion object {

        private const val FILM = "film"

        fun getIntent(context: Context, film: Film): Intent {
            return Intent(context, FilmsDetailsActivity::class.java).apply {
                putExtra(FILM, film)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.films_details)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val film = intent.getParcelableExtra<Film>(FILM)
        setupFilmsDetailsActivity(film)
        setupFavoritesButton(getFilmsFromDatabase(), film)
        setupOpeningCrawlButton(film)
    }

    private fun setupFilmsDetailsActivity(film: Film) {
        collapsingToolbar.apply {
            title = film.title
            setCollapsedTitleTextColor(getColor(R.color.white))
            setExpandedTitleColor(getColor(R.color.white))
        }
        detailsDirector.text = film.director
        detailsEpisodeId.text = film.episodeId
        detailsProducer.text = film.producer
        detailsReleaseDate.text = film.releaseDate
        if (film.inFavorites || compareRemoteWithLocal(getFilmsFromDatabase(), film)) {
            floatingFavoriteButton.setImageResource(R.drawable.favorite)
        }

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun addFilmToFavorites(film: Film) {
        film.inFavorites = true
        GlobalScope.launch(Dispatchers.Main) {
            filmsDatabaseRepository.insertFilm(film)
        }
    }

    private fun setupFavoritesButton(list: MutableList<Film>, film: Film) {
        if (film.inFavorites) {
            floatingFavoriteButton.setOnClickListener {
                deleteFromFavoritesDialogFragment.show(supportFragmentManager, "tag")
                deleteFromFavoritesDialogFragment.deleteDecision = { decision ->
                    deleteFromFavoritesAnimation(film, decision)
                }
            }
        } else {
            floatingFavoriteButton.setOnClickListener {
                if (compareRemoteWithLocal(list, film)) {
                    deleteFromFavoritesDialogFragment.show(supportFragmentManager, "tag")
                    deleteFromFavoritesDialogFragment.deleteDecision = { decision ->
                        deleteFromFavoritesAnimation(film, decision)
                    }
                } else {
                    addToFavoritesAnimation(film)
                }
            }
        }
    }

    private fun setupOpeningCrawlButton(film: Film) {
        openingCrawlButton.setOnClickListener {
            startOpeningCrawlActivity(film)
        }
    }

    private fun startOpeningCrawlActivity(film: Film) {
        val intent = FilmOpeningCrawlActivity.getIntent(this, film)
        startActivity(intent)
    }

    private fun getFilmsFromDatabase(): MutableList<Film> {
        val list = mutableListOf<Film>()
        GlobalScope.launch {
            val database = filmsDatabaseRepository.getFavoriteFilms()
            list.addAll(database)
        }
        return list
    }

    private fun compareRemoteWithLocal(list: MutableList<Film>, film: Film): Boolean {
        return list.contains(film)
    }

    private fun deleteFilmFromDatabase(film: Film, decision: Boolean) {
        if (decision) {
            GlobalScope.launch(Dispatchers.Main) {
                filmsDatabaseRepository.deleteFilm(film)
            }
        }
    }

    private fun addToFavoritesAnimation(film: Film) {
        val animation = AnimationUtils.loadAnimation(this, R.anim.add_to_favorites_anim)
        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {}
            override fun onAnimationEnd(animation: Animation?) {
                floatingFavoriteButton.setImageResource(R.drawable.favorite)
                floatingFavoriteButton.isEnabled = false
                addFilmToFavorites(film)
            }
            override fun onAnimationStart(animation: Animation?) {
                animation?.start()
            }
        })
        floatingFavoriteButton.startAnimation(animation)
    }

    private fun deleteFromFavoritesAnimation(film: Film, decision: Boolean) {
        val animation = AnimationUtils.loadAnimation(this, R.anim.add_to_favorites_anim)
        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {}
            override fun onAnimationEnd(animation: Animation?) {
                floatingFavoriteButton.setImageResource(R.drawable.favorite_border)
                deleteFilmFromDatabase(film, decision)
                floatingFavoriteButton.isEnabled = false
            }
            override fun onAnimationStart(animation: Animation?) {
                animation?.start()
            }
        })
        floatingFavoriteButton.startAnimation(animation)
    }
}
