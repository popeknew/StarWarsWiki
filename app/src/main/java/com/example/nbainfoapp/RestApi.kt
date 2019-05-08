package com.example.nbainfoapp

import com.example.nbainfoapp.model.FilmsListModel
import com.example.nbainfoapp.model.PeopleListModel
import com.example.nbainfoapp.model.PlanetsListModel
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface RestApi {

    @GET("people/")
    fun getPeople() : Deferred<PeopleListModel>

    @GET("people/")
    fun getPeopleByPage(@Query("page")pageNumber: Int) : Deferred<PeopleListModel>

    @GET("planets/")
    fun getPlanetsByPage(@Query("page")pageNumber: Int) : Deferred<PlanetsListModel>

    @GET("films/")
    fun getAllFilms() : Deferred<FilmsListModel>
}