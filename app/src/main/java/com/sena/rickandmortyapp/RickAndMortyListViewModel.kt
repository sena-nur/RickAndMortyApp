package com.sena.rickandmortyapp

import android.os.Parcelable
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RickAndMortyListViewModel: ViewModel() {
    var lastVisiblePosition = 0
    val residentIdList = mutableListOf<String>()
    val selectedLocationIdList = mutableListOf<Int>()
    lateinit var locationsAdapter: LocationsAdapter
    lateinit var charactersAdapter: CharactersAdapter
    var currentPage = 1
    var locationList : MutableList<LocationModel> = mutableListOf()
    private var lastScrollX = 0
    var recyclerViewState : Parcelable? = null

    val locationListResponse: MutableLiveData<MutableList<LocationModel>> by lazy {
        MutableLiveData(mutableListOf())
    }
    val allCharactersResponse: MutableLiveData<MutableList<CharacterModel>> by lazy {
        MutableLiveData(mutableListOf())
    }
    val multipleCharactersResponse: MutableLiveData<MutableList<CharacterModel>> by lazy {
        MutableLiveData(mutableListOf())
    }
    init {
        getLocations(currentPage)
    }
    fun getLocations(pageIndex:Int){
        val serviceGenerator = ApiClient.buildService(ApiInterface::class.java)
        val call = serviceGenerator.getLocations(pageIndex)
        call.enqueue(object: Callback<LocationListResponse> {
            override fun onResponse(call: Call<LocationListResponse>, response: Response<LocationListResponse>) {
                if(response.isSuccessful) {
                    locationList.addAll(response.body()!!.results)
                    locationListResponse.postValue(locationList)

                } else{
                    Log.d("unsuccess",response.toString())
                }
            }
            override fun onFailure(call: Call<LocationListResponse>, t:Throwable) {
                Log.e("Failure", t.message.toString())
            }
        })
    }
    fun getMultipleCharacters(idList:String){
        val serviceGenerator = ApiClient.buildService(ApiInterface::class.java)
        val call = serviceGenerator.getMultipleCharacters(idList)
        call.enqueue(object: Callback<MutableList<CharacterModel>> {
            override fun onResponse(call: Call<MutableList<CharacterModel>>, response: Response<MutableList<CharacterModel>>) {
                if(response.isSuccessful) {
                    response.body()?.let {
                        multipleCharactersResponse.postValue(it)
                    }
                } else{
                    Log.d("unsuccess",response.toString())
                }
            }
            override fun onFailure(call: Call<MutableList<CharacterModel>>, t:Throwable) {
                Log.e("Failure", t.message.toString())
            }
        })
    }

    fun getAllCharacters(){
        val serviceGenerator = ApiClient.buildService(ApiInterface::class.java)
        val call = serviceGenerator.getAllCharacters()
        call.enqueue(object: Callback<CharacterListResponse> {
            override fun onResponse(call: Call<CharacterListResponse>, response: Response<CharacterListResponse>) {
                if(response.isSuccessful) {
                    response.body()?.let {
                        allCharactersResponse.postValue(it.results)
                    }
                } else{
                    Log.d("unsuccess",response.toString())
                }
            }
            override fun onFailure(call: Call<CharacterListResponse>, t:Throwable) {
                Log.e("Failure", t.message.toString())
            }
        })
    }
}