package com.example.nbainfoapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.nbainfoapp.database.dao.PlanetDao
import com.example.nbainfoapp.model.Planet

@Database(entities = arrayOf(Planet::class), version = 4)
abstract class PlanetsDatabase: RoomDatabase() {
    abstract fun planetDao(): PlanetDao
}