package com.example.nbainfoapp.model

import com.google.gson.annotations.SerializedName

data class ScoreModel(
    @SerializedName("GameID")
    val gameId: String,
    @SerializedName("StartPeriod")
    val startPeriod: String,
    @SerializedName("EndPeriod")
    val endPeriod: String,
    @SerializedName("StartRange")
    val startRange: String,
    @SerializedName("EndRange")
    val endRange: String,
    @SerializedName("RangeType")
    val rangeType: String
)