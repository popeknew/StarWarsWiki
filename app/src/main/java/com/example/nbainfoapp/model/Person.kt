package com.example.nbainfoapp.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class Person(
    @SerializedName("name")
    val name: String,
    @SerializedName("birth_year")
    val birthYear: String,
    @SerializedName("eye_color")
    val eyeColor: String,
    @SerializedName("gender")
    val gender: String,
    @SerializedName("hair_color")
    val hairColor: String,
    @SerializedName("height")
    val height: String,
    @SerializedName("homeworld")
    val homeworld: String,
    @SerializedName("mass")
    val mass: String,
    @SerializedName("skin_color")
    val skinColor: String,
    var inFavorites: Boolean,
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null
) : Parcelable {

    override fun hashCode() = name.hashCode()

    override fun equals(other: Any?) = other is Person && name == other.name
}