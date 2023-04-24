package com.sena.rickandmortyapp

import com.google.gson.annotations.SerializedName

class CharacterListResponse (
    @SerializedName("info") val info: InfoModel,
    @SerializedName("results") val results:MutableList<CharacterModel>
):java.io.Serializable