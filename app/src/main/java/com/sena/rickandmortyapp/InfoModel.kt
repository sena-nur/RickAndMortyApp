package com.sena.rickandmortyapp

import com.google.gson.annotations.SerializedName

class InfoModel (
    @SerializedName("count") val count: Int,
    @SerializedName("pages") val pages: Int,
    @SerializedName("next") val next: String,
    @SerializedName("prev") val prev: String
):java.io.Serializable