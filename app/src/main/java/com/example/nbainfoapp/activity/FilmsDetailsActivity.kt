package com.example.nbainfoapp.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Telephony.TextBasedSmsColumns.PERSON
import android.view.Gravity
import android.view.MenuItem
import com.example.nbainfoapp.R
import com.example.nbainfoapp.model.FilmModel
import com.example.nbainfoapp.model.PersonModel
import kotlinx.android.synthetic.main.films_details.*

class FilmsDetailsActivity : AppCompatActivity() {

    companion object {

        private const val FILM = "film"

        fun getIntent(context: Context, filmModel: FilmModel): Intent {
            return Intent(context, FilmsDetailsActivity::class.java).apply {
                putExtra(FILM, filmModel)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.films_details)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val film = intent.getParcelableExtra<FilmModel>(FILM)
        setupFilmsDetailsActivity(film)
    }

    private fun setupFilmsDetailsActivity(filmModel: FilmModel) {
        collapsingToolbar.apply {
            title = filmModel.title
            setCollapsedTitleTextColor(getColor(R.color.white))
            setExpandedTitleColor(getColor(R.color.white))
           // expandedTitleGravity = Gravity.START
        }
        detailsDirector.text = filmModel.director
        detailsEpisodeId.text = filmModel.episodeId
        detailsProducer.text = filmModel.producer
        detailsReleaseDate.text = filmModel.releaseDate
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }
}
