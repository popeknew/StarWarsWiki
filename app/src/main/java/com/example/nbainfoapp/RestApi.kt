package com.example.nbainfoapp

import com.example.nbainfoapp.model.ScoreModel
import kotlinx.coroutines.Deferred
import retrofit2.http.GET

interface RestApi {

    @GET("/boxscoreadvancedv2")
    fun getStats() : Deferred<ScoreModel>
}