package com.example.nbainfoapp.repository

import com.example.nbainfoapp.database.PeopleDatabase
import com.example.nbainfoapp.model.Person
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class PeopleDatabaseRepository(val database: PeopleDatabase) {

    val personDao = database.personDao()

    suspend fun insertPerson(person: Person) = withContext(Dispatchers.IO) {
        personDao.insertPerson(person)
    }

    suspend fun deletePerson(person: Person) = withContext(Dispatchers.IO) {
        personDao.deletePerson(person)
    }

    suspend fun getFavoritePeople() : MutableList<Person> = withContext(Dispatchers.IO) {
        personDao.getFavoritePeople()
    }
}