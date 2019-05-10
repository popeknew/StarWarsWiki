package com.example.nbainfoapp.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class Planet(
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
    val rotationPeriod: String,
    var inFavorites: Boolean,
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null
): Parcelable {

    override fun hashCode() = name.hashCode()

    override fun equals(other: Any?) = other is Person && name == other.name

}