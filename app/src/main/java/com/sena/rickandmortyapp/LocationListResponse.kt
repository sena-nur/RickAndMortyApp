package com.sena.rickandmortyapp

import com.google.gson.annotations.SerializedName

class LocationListResponse (
    @SerializedName("info") val info: InfoModel,
    @SerializedName("results") val results:MutableList<LocationModel>
    ):java.io.Serializable