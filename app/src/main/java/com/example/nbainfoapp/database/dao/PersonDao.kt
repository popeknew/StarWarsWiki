package com.example.nbainfoapp.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.nbainfoapp.model.Person

@Dao
interface PersonDao {

    @Query("SELECT * FROM person")
    fun getFavoritePeople() : MutableList<Person>

    @Insert
    fun insertPerson(person: Person)

    @Delete
    fun deletePerson(person: Person)
}