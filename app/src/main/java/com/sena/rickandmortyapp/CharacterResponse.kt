package com.sena.rickandmortyapp

import com.google.gson.annotations.SerializedName

class CharacterResponse (
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("status") val status: String,
    @SerializedName("species") val species: String,
    @SerializedName("type") val type: String,
    @SerializedName("gender") val gender: String,
    @SerializedName("origin") val origin: OriginModel,
    @SerializedName("location") val location: SmallLocationModel,
    @SerializedName("image") val image: String,
    @SerializedName("episode") val episode: MutableList<String>,
    @SerializedName("url") val url: String,
    @SerializedName("created") val created: String
):java.io.Serializable