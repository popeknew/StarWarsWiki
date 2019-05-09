package com.example.nbainfoapp.model

import com.google.gson.annotations.SerializedName

data class PlanetsListModel(
    @SerializedName("results")
    val planets: List<Planet>
)