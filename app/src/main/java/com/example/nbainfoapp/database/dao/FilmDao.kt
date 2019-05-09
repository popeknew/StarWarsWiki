package com.example.nbainfoapp.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.nbainfoapp.model.Film

@Dao
interface FilmDao {

    @Query("SELECT * FROM film")
    fun getFavoriteFilms() : MutableList<Film>

    @Insert
    fun insertFilm(film: Film)

    @Delete
    fun deleteFilm(film: Film)
}