package com.example.nbainfoapp.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FilmModel(
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
    val releaseDate: String? = null
): Parcelable