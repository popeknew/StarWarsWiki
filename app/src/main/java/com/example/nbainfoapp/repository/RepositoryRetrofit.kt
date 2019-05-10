package com.example.nbainfoapp.repository

import com.example.nbainfoapp.RestApi
import com.example.nbainfoapp.model.Film
import com.example.nbainfoapp.model.Person
import com.example.nbainfoapp.model.Planet

class RepositoryRetrofit(private val api: RestApi) {

    suspend fun getPeople(numberOfPage: Int) : MutableList<Person> {
        val listOfPeople = mutableListOf<Person>()
        val peopleFromServer = api.getPeopleByPage(numberOfPage).await()
        for (i in peopleFromServer.people) {
            val newPerson = Person(i.name, i.birthYear, i.eyeColor, i.gender, i.hairColor, i.height, i.homeworld, i.mass, i.skinColor, false)
            listOfPeople.add(newPerson)
        }
        return listOfPeople
    }

    suspend fun getPlanets(numberOfPage: Int) : MutableList<Planet> {
        val listOfPlanets = mutableListOf<Planet>()
        val planetsFromServer = api.getPlanetsByPage(numberOfPage).await()
        for (i in planetsFromServer.planets) {
            val newPlanet = Planet(i.name, i.diameter, i.climate, i.gravity, i.population, i.terrain, i.surfaceWater, i.orbitalPeriod, i.rotationPeriod, false)
            listOfPlanets.add(newPlanet)
        }
        return listOfPlanets
    }

    suspend fun getFilms() : MutableList<Film> {
        val listOfFilms = mutableListOf<Film>()
        val filmsFromServer = api.getAllFilms().await()
        for (i in filmsFromServer.films) {
            val newFilm = Film(i.title, i.episodeId, i.openingCrawl, i.director, i.producer, i.releaseDate, false)
            listOfFilms.add(newFilm)
        }
        return listOfFilms
    }
}