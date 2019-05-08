package com.example.nbainfoapp.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PlanetModel(
    @SerializedName("name")
    val name: String,
    @SerializedName("diameter")
    val diameter: String,
    @SerializedName("climate")
    val climate: String,
    @SerializedName("gravity")
    val gravity: String,
    @SerializedName("population")
    val population: String,
    @SerializedName("terrain")
    val terrain: String,
    @SerializedName("surface_water")
    val surfaceWater: String,
    @SerializedName("orbital_period")
    val orbitalPeriod: String,
    @SerializedName("rotation_period")
    val rotationPeriod: String
): Parcelable