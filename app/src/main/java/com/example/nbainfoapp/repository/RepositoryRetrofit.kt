package com.example.nbainfoapp.repository

import com.example.nbainfoapp.RestApi
import com.example.nbainfoapp.model.FilmModel
import com.example.nbainfoapp.model.PersonModel
import com.example.nbainfoapp.model.PlanetModel

class RepositoryRetrofit(private val api: RestApi) {

    suspend fun getPeople(numberOfPage: Int) : MutableList<PersonModel> {
        val listOfPeople = mutableListOf<PersonModel>()
        val peopleFromServer = api.getPeopleByPage(numberOfPage).await()
        for (i in peopleFromServer.people) {
            val newPerson = PersonModel(i.name, i.birthYear, i.eyeColor, i.gender, i.hairColor, i.height, i.homeworld, i.mass, i.skinColor)
            listOfPeople.add(newPerson)
        }
        return listOfPeople
    }

    suspend fun getPlanets(numberOfPage: Int) : MutableList<PlanetModel> {
        val listOfPlanets = mutableListOf<PlanetModel>()
        val planetsFromServer = api.getPlanetsByPage(numberOfPage).await()
        for (i in planetsFromServer.planets) {
            val newPlanet = PlanetModel(i.name, i.diameter, i.climate, i.gravity, i.population, i.terrain, i.surfaceWater, i.orbitalPeriod, i.rotationPeriod)
            listOfPlanets.add(newPlanet)
        }
        return listOfPlanets
    }

    suspend fun getFilms() : MutableList<FilmModel> {
        val listOfFilms = mutableListOf<FilmModel>()
        val filmsFromServer = api.getAllFilms().await()
        for (i in filmsFromServer.films) {
            val newFilm = FilmModel(i.title, i.episodeId, i.openingCrawl, i.director, i.producer, i.releaseDate)
            listOfFilms.add(newFilm)
        }
        return listOfFilms
    }
}