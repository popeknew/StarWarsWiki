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
import com.example.nbainfoapp.model.Person
import com.example.nbainfoapp.model.Planet
import com.example.nbainfoapp.repository.PlanetsDatabaseRepository
import kotlinx.android.synthetic.main.planets_details.collapsingToolbar
import kotlinx.android.synthetic.main.planets_details.detailsDirector
import kotlinx.android.synthetic.main.planets_details.detailsEpisodeId
import kotlinx.android.synthetic.main.planets_details.detailsOpeningCrawl
import kotlinx.android.synthetic.main.planets_details.detailsOrbitalPeriod
import kotlinx.android.synthetic.main.planets_details.detailsProducer
import kotlinx.android.synthetic.main.planets_details.detailsReleaseDate
import kotlinx.android.synthetic.main.planets_details.detailsRotationPeriod
import kotlinx.android.synthetic.main.planets_details.detailsSurfaceWater
import kotlinx.android.synthetic.main.planets_details.floatingFavoriteButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class PlanetsDetailsActivity : AppCompatActivity(), KodeinAware {

    override val kodein by kodein()
    private val planetsDatabaseRepository: PlanetsDatabaseRepository by instance()
    val deleteFromFavoritesDialogFragment = DeleteFromFavoritesDialogFragment()

    companion object {

        private const val PLANET = "person"

        fun getIntent(context: Context, planet: Planet): Intent {
            return Intent(context, PlanetsDetailsActivity::class.java).apply {
                putExtra(PLANET, planet)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.planets_details)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val planet: Planet = intent.getParcelableExtra(PLANET)
        setupPlanetsDetailsActivity(planet)
        setupFavoritesButton(getPlanetsFromDatabase(), planet)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupPlanetsDetailsActivity(planet: Planet) {
        collapsingToolbar.apply {
            title = planet.name
            setExpandedTitleColor(getColor(R.color.white))
            setCollapsedTitleTextColor(getColor(R.color.white))
        }
        detailsEpisodeId.text = planet.climate
        detailsDirector.text = planet.diameter
        detailsProducer.text = planet.gravity
        detailsOrbitalPeriod.text = planet.orbitalPeriod
        detailsOpeningCrawl.text = planet.population
        detailsSurfaceWater.text = planet.surfaceWater
        detailsRotationPeriod.text = planet.rotationPeriod
        detailsReleaseDate.text = planet.terrain
        if (planet.inFavorites || compareRemoteWithLocal(getPlanetsFromDatabase(), planet)) {
            floatingFavoriteButton.setImageResource(R.drawable.favorite)
        }
    }

    private fun addPlanetToFavorites(planet: Planet) {
        planet.inFavorites = true
        GlobalScope.launch {
            planetsDatabaseRepository.insertPlanet(planet)
        }
    }

    private fun setupFavoritesButton(list: MutableList<Planet>, planet: Planet) {
        if (planet.inFavorites) {
            floatingFavoriteButton.setOnClickListener {
                deleteFromFavoritesDialogFragment.show(supportFragmentManager, "tag")
                deleteFromFavoritesDialogFragment.deleteDecision = { decision ->
                    deleteFromFavoritesAnimation(planet, decision)
                }
            }
        } else {
            floatingFavoriteButton.setOnClickListener {
                if (compareRemoteWithLocal(list, planet)) {
                    deleteFromFavoritesDialogFragment.show(supportFragmentManager, "tag")
                    deleteFromFavoritesDialogFragment.deleteDecision = { decision ->
                        deleteFromFavoritesAnimation(planet, decision)
                    }
                } else {
                    addToFavoritesAnimation(planet)
                }
            }
        }
    }

    private fun getPlanetsFromDatabase(): MutableList<Planet> {
        val list = mutableListOf<Planet>()
        GlobalScope.launch {
            val database = planetsDatabaseRepository.getFavoritePlanets()
            list.addAll(database)
        }
        return list
    }

    private fun deletePlanetFromDatabase(planet: Planet, decision: Boolean) {
        if (decision) {
            GlobalScope.launch(Dispatchers.Main) {
                planetsDatabaseRepository.deletePlanet(planet)
            }
        }
    }

    private fun compareRemoteWithLocal(list: MutableList<Planet>, planet: Planet): Boolean {
        return list.contains(planet)
    }

    private fun addToFavoritesAnimation(planet: Planet) {
        val animation = AnimationUtils.loadAnimation(this, R.anim.add_to_favorites_anim)
        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {}
            override fun onAnimationEnd(animation: Animation?) {
                floatingFavoriteButton.setImageResource(R.drawable.favorite)
                floatingFavoriteButton.isEnabled = false
                addPlanetToFavorites(planet)
            }
            override fun onAnimationStart(animation: Animation?) {
                animation?.start()
            }
        })
        floatingFavoriteButton.startAnimation(animation)
    }

    private fun deleteFromFavoritesAnimation(planet: Planet, decision: Boolean) {
        val animation = AnimationUtils.loadAnimation(this, R.anim.remove_from_favorites_anim)
        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {}
            override fun onAnimationEnd(animation: Animation?) {
                floatingFavoriteButton.setImageResource(R.drawable.favorite_border)
                deletePlanetFromDatabase(planet, decision)
                floatingFavoriteButton.isEnabled = false
            }
            override fun onAnimationStart(animation: Animation?) {
                animation?.start()
            }
        })
        floatingFavoriteButton.startAnimation(animation)
    }
}
