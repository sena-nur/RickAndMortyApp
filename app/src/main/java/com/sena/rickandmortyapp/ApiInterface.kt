package com.sena.rickandmortyapp

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface  {
    @GET("location")
    fun getLocations(@Query("page") page: Int): Call<LocationListResponse>
    @GET("character/{idList}")
    fun getMultipleCharacters(@Path("idList") idList: String): Call<MutableList<CharacterModel>>
    @GET("character")
    fun getAllCharacters(): Call<CharacterListResponse>
}