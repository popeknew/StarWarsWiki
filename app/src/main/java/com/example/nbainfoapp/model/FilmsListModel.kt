package com.example.nbainfoapp.model

import com.google.gson.annotations.SerializedName

data class FilmsListModel(
    @SerializedName("results")
    val films: List<FilmModel>
)