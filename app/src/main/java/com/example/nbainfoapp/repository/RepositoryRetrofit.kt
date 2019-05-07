package com.example.nbainfoapp.repository

import com.example.nbainfoapp.RestApi
import com.example.nbainfoapp.model.PersonModel

class RepositoryRetrofit(private val api: RestApi) {

    suspend fun getPeople(numberOfPage: Int) : MutableList<PersonModel> {
        val listOfPeople = mutableListOf<PersonModel>()
        val peopleFromServer = api.getPeopleByPage(numberOfPage).await()
        for (i in peopleFromServer.people) {
            val newPerson = PersonModel(i.name, i.birthYear, i.eyeColor, i.gender, i.hairColor, i.height, i.homeworld, i.mass, i.skinColor, i.films)
            listOfPeople.add(newPerson)
        }
        return listOfPeople
    }
}