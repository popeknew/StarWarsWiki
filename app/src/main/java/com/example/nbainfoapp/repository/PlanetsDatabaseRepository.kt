package com.example.nbainfoapp.repository

import com.example.nbainfoapp.database.PlanetsDatabase
import com.example.nbainfoapp.model.Planet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PlanetsDatabaseRepository(val planetsDatabase: PlanetsDatabase) {

    val planetDao = planetsDatabase.planetDao()

    suspend fun insertPlanet(planet: Planet) = withContext(Dispatchers.IO) {
        planetDao.insertPlanet(planet)
    }

    suspend fun deletePlanet(planet: Planet) = withContext(Dispatchers.IO) {
        planetDao.deletePlanet(planet)
    }

    suspend fun getFavoritePlanets() : MutableList<Planet> = withContext(Dispatchers.IO) {
        planetDao.getFavoritePlanets()
    }
}