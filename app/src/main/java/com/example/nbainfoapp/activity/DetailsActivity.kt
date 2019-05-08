package com.example.nbainfoapp.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.nbainfoapp.R
import com.example.nbainfoapp.model.PersonModel
import kotlinx.android.synthetic.main.people_details.*

class DetailsActivity : AppCompatActivity() {

    companion object {

        private const val PERSON = "person"

        fun getIntent(context: Context, personModel: PersonModel): Intent {
            return Intent(context, DetailsActivity::class.java).apply {
                putExtra(PERSON, personModel)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.people_details)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        val person = intent.getParcelableExtra<PersonModel>(PERSON)

        setupDetailsActivity(person)
    }

    private fun setupDetailsActivity(personModel: PersonModel) {
        collapsingToolbar.title = personModel.name
        detailsHeight.text = personModel.height
        detailsEyeColor.text = personModel.eyeColor
        detailsFilms.text = personModel.films.toString()
        detailsGender.text = personModel.gender
        detailsHairColor.text = personModel.hairColor
        detailsHomeworld.text = personModel.homeworld
        detailsMass.text = personModel.mass
        detailsSkinColor.text = personModel.skinColor
        detailsBirthYear.text = personModel.birthYear
    }

   // private fun
}
