package com.example.nbainfoapp.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.example.nbainfoapp.R
import com.example.nbainfoapp.fragment.DeleteFromFavoritesDialogFragment
import com.example.nbainfoapp.fragment.ShowDetailsImageDialog
import com.example.nbainfoapp.helper.AssetsPathConverter
import com.example.nbainfoapp.model.Person
import com.example.nbainfoapp.repository.PeopleDatabaseRepository
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.people_details.*
import kotlinx.android.synthetic.main.people_details.collapsingToolbar
import kotlinx.android.synthetic.main.people_details.collapsingToolbarImage
import kotlinx.android.synthetic.main.people_details.floatingFavoriteButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance


class PeopleDetailsActivity : AppCompatActivity(), KodeinAware {

    override val kodein by kodein()
    private val peopleDatabaseRepository by instance<PeopleDatabaseRepository>()
    private val deleteFromFavoritesDialogFragment = DeleteFromFavoritesDialogFragment()
    private val assetsPathConverter = AssetsPathConverter()

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
        setupFavoritesButton(getPeoplesFromDatabase(), person)
        setupImageFullscreen(person.name)
    }

    private fun setupPeopleDetailsActivity(person: Person) {
        collapsingToolbar.apply {
            title = person.name
            setCollapsedTitleTextColor(getColor(R.color.white))
            setExpandedTitleColor(getColor(R.color.white))
        }
        Picasso.get()
            .load(assetsPathConverter.createAssetsAddress(person.name))
            .into(collapsingToolbarImage)
        detailsHeight.text = person.height
        detailsEyeColor.text = person.eyeColor
        detailsGender.text = person.gender
        detailsHairColor.text = person.hairColor
        detailsHomeworld.text = person.homeworld
        detailsMass.text = person.mass
        detailsSkinColor.text = person.skinColor
        detailsBirthYear.text = person.birthYear
        if (person.inFavorites || compareRemoteWithLocal(getPeoplesFromDatabase(), person)) {
            floatingFavoriteButton.setImageResource(R.drawable.favorite)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun addPersonToFavorites(person: Person) {
        person.inFavorites = true
        GlobalScope.launch {
            peopleDatabaseRepository.insertPerson(person)
        }
    }

    private fun setupFavoritesButton(list: MutableList<Person>, person: Person) {
        if (person.inFavorites) {
            floatingFavoriteButton.setOnClickListener {
                deleteFromFavoritesDialogFragment.show(supportFragmentManager, "tag")
                deleteFromFavoritesDialogFragment.deleteDecision = { decision ->
                    deleteFromFavoritesAnimation(person, decision)
                }
            }
        } else {
            floatingFavoriteButton.setOnClickListener {
                if (compareRemoteWithLocal(list, person)) {
                    deleteFromFavoritesDialogFragment.show(supportFragmentManager, "tag")
                    deleteFromFavoritesDialogFragment.deleteDecision = { decision ->
                        deleteFromFavoritesAnimation(person, decision)
                    }
                } else {
                    addToFavoritesAnimation(person)
                }
            }
        }
    }

    private fun deletePersonFromDatabase(person: Person, decision: Boolean) {
        if (decision) {
            GlobalScope.launch(Dispatchers.Main) {
                peopleDatabaseRepository.deletePerson(person)
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

    private fun addToFavoritesAnimation(person: Person) {
        val animation = AnimationUtils.loadAnimation(this, R.anim.add_to_favorites_anim)
        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {}
            override fun onAnimationEnd(animation: Animation?) {
                floatingFavoriteButton.setImageResource(R.drawable.favorite)
                floatingFavoriteButton.isEnabled = false
                addPersonToFavorites(person)
            }

            override fun onAnimationStart(animation: Animation?) {
                animation?.start()
            }
        })
        floatingFavoriteButton.startAnimation(animation)
    }

    private fun deleteFromFavoritesAnimation(person: Person, decision: Boolean) {
        val animation = AnimationUtils.loadAnimation(this, R.anim.remove_from_favorites_anim)
        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {}
            override fun onAnimationEnd(animation: Animation?) {
                floatingFavoriteButton.setImageResource(R.drawable.favorite_border)
                deletePersonFromDatabase(person, decision)
                floatingFavoriteButton.isEnabled = false
            }

            override fun onAnimationStart(animation: Animation?) {
                animation?.start()
            }
        })
        floatingFavoriteButton.startAnimation(animation)
    }

    private fun setupImageFullscreen(name: String) {
        collapsingToolbarImage.setOnClickListener {
            val dialog = ShowDetailsImageDialog(name)
            dialog.show(supportFragmentManager, "tag")
        }
    }
}
