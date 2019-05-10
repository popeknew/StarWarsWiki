package com.example.nbainfoapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.nbainfoapp.database.dao.PersonDao
import com.example.nbainfoapp.model.Person

@Database(entities = arrayOf(Person::class), version = 4)
abstract class PeopleDatabase : RoomDatabase() {
    abstract fun personDao(): PersonDao
}