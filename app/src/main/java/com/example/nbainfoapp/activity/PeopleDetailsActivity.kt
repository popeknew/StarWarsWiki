package com.example.nbainfoapp.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.example.nbainfoapp.R
import com.example.nbainfoapp.model.Person
import com.example.nbainfoapp.repository.PeopleDatabaseRepository
import kotlinx.android.synthetic.main.people_details.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class PeopleDetailsActivity : AppCompatActivity(), KodeinAware {

    override val kodein by kodein()
    private val peopleDatabaseRepository: PeopleDatabaseRepository by instance()

    companion object {

        private const val PERSON = "person"

        fun getIntent(context: Context, person: Person): Intent {
            return Intent(context, PeopleDetailsActivity::class.java).apply {
                putExtra(PERSON, person)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.people_details)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val person = intent.getParcelableExtra<Person>(PERSON)
        setupPeopleDetailsActivity(person)
        setupFavoritesButton(person)
    }

    private fun setupPeopleDetailsActivity(person: Person) {
        collapsingToolbar.apply {
            title = person.name
            setCollapsedTitleTextColor(getColor(R.color.white))
            setExpandedTitleColor(getColor(R.color.white))
        }
        detailsDirector.text = person.height
        detailsRotationPeriod.text = person.eyeColor
        detailsOrbitalPeriod.text = person.gender
        detailsProducer.text = person.hairColor
        detailsSurfaceWater.text = person.homeworld
        detailsEpisodeId.text = person.mass
        detailsReleaseDate.text = person.skinColor
        detailsOpeningCrawl.text = person.birthYear
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun addString(list: Array<String>): String {
        return list.joinToString(separator = ", ")
    }

    private fun addPersonToFavorites(person: Person) {
        GlobalScope.launch {
            peopleDatabaseRepository.insertPerson(person)
        }
    }

    private fun setupFavoritesButton(person: Person) {
        floatingFavoriteButton.setOnClickListener {
             addPersonToFavorites(person)
        }
    }
}
