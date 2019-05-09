package com.example.nbainfoapp.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.nbainfoapp.model.Planet

@Dao
interface PlanetDao {

    @Query("SELECT * FROM planet")
    fun getFavoritePlanets() : MutableList<Planet>

    @Insert
    fun insertPlanet(planet: Planet)

    @Delete
    fun deletePlanet(planet: Planet)
}