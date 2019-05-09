package com.example.nbainfoapp.model

import com.google.gson.annotations.SerializedName

data class PeopleListModel(
    @SerializedName("results")
    val people: List<Person>
)