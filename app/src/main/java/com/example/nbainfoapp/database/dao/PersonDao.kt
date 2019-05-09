package com.example.nbainfoapp.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.nbainfoapp.model.PersonModel

@Dao
interface PersonDao {

    @Query("SELECT * FROM personmodel")
    fun getFavoritePeople() : MutableList<PersonModel>

    @Insert
    fun insertPerson(personModel: PersonModel)

    @Delete
    fun deletePerson(personModel: PersonModel)
}