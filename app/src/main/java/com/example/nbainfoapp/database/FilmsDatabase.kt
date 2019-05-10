package com.example.nbainfoapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.nbainfoapp.database.dao.FilmDao
import com.example.nbainfoapp.model.Film

@Database(entities = arrayOf(Film::class), version = 4)
abstract class FilmsDatabase : RoomDatabase() {
    abstract fun filmDao(): FilmDao
}