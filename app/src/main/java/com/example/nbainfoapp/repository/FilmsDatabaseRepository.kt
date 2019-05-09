package com.example.nbainfoapp.repository

import com.example.nbainfoapp.database.FilmsDatabase
import com.example.nbainfoapp.model.Film
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FilmsDatabaseRepository(val filmsDatabase: FilmsDatabase) {

    val filmDao = filmsDatabase.filmDao()

    suspend fun getFavoriteFilms() : MutableList<Film> = withContext(Dispatchers.IO) {
        filmDao.getFavoriteFilms()
    }

    suspend fun insertFilm(film: Film) = withContext(Dispatchers.IO) {
        filmDao.insertFilm(film)
    }

    suspend fun deleteFilm(film: Film) = withContext(Dispatchers.IO) {
        filmDao.deleteFilm(film)
    }
}