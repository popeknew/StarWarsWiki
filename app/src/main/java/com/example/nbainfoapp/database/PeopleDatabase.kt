package com.example.nbainfoapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.nbainfoapp.database.dao.PersonDao
import com.example.nbainfoapp.model.PersonModel

@Database(entities = arrayOf(PersonModel::class), version = 1)
abstract class PeopleDatabase : RoomDatabase() {
    abstract fun personDao(): PersonDao
}