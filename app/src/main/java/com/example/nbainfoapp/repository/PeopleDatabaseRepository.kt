package com.example.nbainfoapp.repository

import com.example.nbainfoapp.database.PeopleDatabase
import com.example.nbainfoapp.model.PersonModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class PeopleDatabaseRepository(val database: PeopleDatabase) {

    val personDao = database.personDao()

    suspend fun insertPerson(personModel: PersonModel) = withContext(Dispatchers.IO) {
        personDao.insertPerson(personModel)
    }

    suspend fun deletePerson(personModel: PersonModel) = withContext(Dispatchers.IO) {
        personDao.deletePerson(personModel)
    }

    suspend fun getFavoritePeople() : MutableList<PersonModel> = withContext(Dispatchers.IO) {
        personDao.getFavoritePeople()
    }
}