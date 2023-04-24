package com.sena.rickandmortyapp

import com.google.gson.annotations.SerializedName

class LocationModel (
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("type") val type: String,
    @SerializedName("dimension") val dimension: String,
    @SerializedName("residents") val residents: MutableList<String>,
    @SerializedName("url") val url: String,
    @SerializedName("created") val created: String
):java.io.Serializable