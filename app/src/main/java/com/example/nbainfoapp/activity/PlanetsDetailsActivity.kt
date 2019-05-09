package com.example.nbainfoapp.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.example.nbainfoapp.R
import com.example.nbainfoapp.model.PlanetModel
import kotlinx.android.synthetic.main.planets_details.*

class PlanetsDetailsActivity : AppCompatActivity() {

    companion object {

        private const val PLANET = "person"

        fun getIntent(context: Context, planetModel: PlanetModel): Intent {
            return Intent(context, PlanetsDetailsActivity::class.java).apply {
                putExtra(PLANET, planetModel)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.planets_details)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val planet: PlanetModel = intent.getParcelableExtra(PLANET)
        setupPlanetsDetailsActivity(planet)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupPlanetsDetailsActivity(planetModel: PlanetModel) {
        collapsingToolbar.apply {
            title = planetModel.name
            setExpandedTitleColor(getColor(R.color.white))
            setCollapsedTitleTextColor(getColor(R.color.white))
        }
        detailsEpisodeId.text = planetModel.climate
        detailsDirector.text = planetModel.diameter
        detailsProducer.text = planetModel.gravity
        detailsOrbitalPeriod.text = planetModel.orbitalPeriod
        detailsOpeningCrawl.text = planetModel.population
        detailsSurfaceWater.text = planetModel.surfaceWater
        detailsRotationPeriod.text = planetModel.rotationPeriod
        detailsReleaseDate.text = planetModel.terrain
    }
}
