package com.example.nbainfoapp.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.example.nbainfoapp.R
import com.example.nbainfoapp.model.Film
import kotlinx.android.synthetic.main.film_opening_crawl_layout.*

class FilmOpeningCrawlActivity : AppCompatActivity() {

    companion object {

        private const val FILM = "film"

        fun getIntent(context: Context, film: Film): Intent {
            return Intent(context, FilmOpeningCrawlActivity::class.java).apply {
                putExtra(FILM, film)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.film_opening_crawl_layout)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val film = intent.getParcelableExtra<Film>(FILM)
        setupText(film)
    }

    private fun setupText(film: Film) {
        openigCrawlText.text = film.openingCrawl
        val fromBottomToTopAnim: Animation =
            AnimationUtils.loadAnimation(this, R.anim.from_bottom_to_top)
        openigCrawlText.animation = fromBottomToTopAnim
    }
}
