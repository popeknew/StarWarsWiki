package com.example.nbainfoapp.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class Film(
    @SerializedName("title")
    val title: String,
    @SerializedName("episode_id")
    val episodeId: String? = null,
    @SerializedName("opening_crawl")
    val openingCrawl: String? = null,
    @SerializedName("director")
    val director: String? = null,
    @SerializedName("producer")
    val producer: String? = null,
    @SerializedName("release_date")
    val releaseDate: String? = null,
    var inFavorites: Boolean,
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null
): Parcelable {

    override fun hashCode() = title.hashCode()

    override fun equals(other: Any?) = other is Film && title == other.title
}