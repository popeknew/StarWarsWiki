package com.example.nbainfoapp.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.example.nbainfoapp.R
import com.example.nbainfoapp.model.Film
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
        setupFavoritesButton(film)
        setupOpeningCrawlButton(film)
    }

    private fun setupFilmsDetailsActivity(film: Film) {
        collapsingToolbar.apply {
            title = film.title
            setCollapsedTitleTextColor(getColor(R.color.white))
            setExpandedTitleColor(getColor(R.color.white))
           // expandedTitleGravity = Gravity.START
        }
        detailsDirector.text = film.director
        detailsEpisodeId.text = film.episodeId
        detailsProducer.text = film.producer
        detailsReleaseDate.text = film.releaseDate


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

    private fun setupFavoritesButton(film: Film) {
        if (film.inFavorites) {
            floatingFavoriteButton.hide()
        } else {
            floatingFavoriteButton.setOnClickListener {
                addFilmToFavorites(film)
            }
        }
    }

    private fun setupOpeningCrawlButton(film: Film) {
        butonik.setOnClickListener {
            startOpeningCrawlActivity(film)
        }
    }

    private fun startOpeningCrawlActivity(film: Film) {
        val intent = FilmOpeningCrawlActivity.getIntent(this, film)
        startActivity(intent)
    }
}
