package com.sena.rickandmortyapp

import com.google.gson.annotations.SerializedName

class SmallLocationModel (
    @SerializedName("name") val name: String,
    @SerializedName("url") val url: String
):java.io.Serializable