package com.example.nbainfoapp.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Telephony.TextBasedSmsColumns.PERSON
import android.view.MenuItem
import com.example.nbainfoapp.R
import com.example.nbainfoapp.model.PersonModel
import com.example.nbainfoapp.repository.PeopleDatabaseRepository
import kotlinx.android.synthetic.main.people_details.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class PeopleDetailsActivity : AppCompatActivity(), KodeinAware {

    override val kodein by kodein()
    private val peopleDatabaseRepository: PeopleDatabaseRepository by instance()

    companion object {

        private const val PERSON = "person"

        fun getIntent(context: Context, personModel: PersonModel): Intent {
            return Intent(context, PeopleDetailsActivity::class.java).apply {
                putExtra(PERSON, personModel)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.people_details)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val person = intent.getParcelableExtra<PersonModel>(PERSON)
        setupPeopleDetailsActivity(person)
        setupFavoritesButton(person)
    }

    private fun setupPeopleDetailsActivity(personModel: PersonModel) {
        collapsingToolbar.apply {
            title = personModel.name
            setCollapsedTitleTextColor(getColor(R.color.white))
            setExpandedTitleColor(getColor(R.color.white))
        }
        detailsDirector.text = personModel.height
        detailsRotationPeriod.text = personModel.eyeColor
        detailsOrbitalPeriod.text = personModel.gender
        detailsProducer.text = personModel.hairColor
        detailsSurfaceWater.text = personModel.homeworld
        detailsEpisodeId.text = personModel.mass
        detailsReleaseDate.text = personModel.skinColor
        detailsOpeningCrawl.text = personModel.birthYear
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

    private fun addPersonToFavorites(personModel: PersonModel) {
        GlobalScope.launch {
            peopleDatabaseRepository.insertPerson(personModel)
        }
    }

    private fun setupFavoritesButton(personModel: PersonModel) {
        floatingFavoriteButton.setOnClickListener {
             addPersonToFavorites(personModel)
        }
    }
}
