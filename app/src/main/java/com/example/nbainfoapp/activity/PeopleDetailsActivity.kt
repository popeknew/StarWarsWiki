package com.example.nbainfoapp.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.example.nbainfoapp.R
import com.example.nbainfoapp.model.Person
import com.example.nbainfoapp.repository.PeopleDatabaseRepository
import kotlinx.android.synthetic.main.fragment_people.*
import kotlinx.android.synthetic.main.people_details.*
import kotlinx.coroutines.Dispatchers
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
        private const val REMOTE_LIST = "remoteList"

        fun getIntent(context: Context, person: Person, list: ArrayList<Person>): Intent {
            return Intent(context, PeopleDetailsActivity::class.java).apply {
                putExtra(PERSON, person)
                putExtra(REMOTE_LIST, list)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.people_details)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val person = intent.getParcelableExtra<Person>(PERSON)
        val remoteList: ArrayList<Person> = intent.getParcelableArrayListExtra(REMOTE_LIST)

        setupPeopleDetailsActivity(person)
        setupFavoritesButton(getPeoplesFromDatabase(), person)
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
        person.inFavorites = true
        GlobalScope.launch {
            peopleDatabaseRepository.insertPerson(person)
        }
    }

    private fun setupFavoritesButton(list: MutableList<Person>, person: Person) {
        if (person.inFavorites) {
            floatingFavoriteButton.hide()
        } else {
            floatingFavoriteButton.setOnClickListener {
                if (compareRemoteWithLocal(list, person)) {
                    Toast.makeText(this, "chcesz to wyjebac?", Toast.LENGTH_LONG).show()
                } else {
                    addPersonToFavorites(person)
                    floatingFavoriteButton.isEnabled = false
                }
            }

        }
    }

    private fun getPeoplesFromDatabase(): MutableList<Person> {
        val list = mutableListOf<Person>()
        GlobalScope.launch(Dispatchers.Main) {
            val database = peopleDatabaseRepository.getFavoritePeople()
            list.addAll(database)
        }
        return list
    }

    private fun compareRemoteWithLocal(list: MutableList<Person>, person: Person): Boolean {
        return list.contains(person)
    }
}
