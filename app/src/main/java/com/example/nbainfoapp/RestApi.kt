package com.example.nbainfoapp

import com.example.nbainfoapp.model.PeopleListModel
import com.example.nbainfoapp.model.PersonModel
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RestApi {

    @GET("people/")
    fun getPeople() : Deferred<PeopleListModel>

    @GET("people/")
    fun getPeopleByPage(@Query("page")pageNumber: Int) : Deferred<PeopleListModel>
}