package com.sena.rickandmortyapp

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sena.rickandmortyapp.databinding.FragmentRickandmortylistBinding

class RickAndMortyListFragment : Fragment(R.layout.fragment_rickandmortylist),
    LocationClickInterface, CharacterClickInterface {
    private val viewModel: RickAndMortyListViewModel by viewModels()
    private var _binding: FragmentRickandmortylistBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentRickandmortylistBinding.bind(view)
        setObservers()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun checkLocationListScrolled(){
        binding.rvLocation.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                viewModel.recyclerViewState = binding.rvLocation.layoutManager?.onSaveInstanceState()
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    val canScrollRight = recyclerView.canScrollHorizontally(1)
                    if (!canScrollRight) {
                        viewModel.currentPage++
                        viewModel.getLocations(viewModel.currentPage)
                    }


            }
        }
    })
    }

    fun setObservers() {
        viewModel.locationListResponse.observe(viewLifecycleOwner) {
            it?.let {
                viewModel.locationsAdapter = LocationsAdapter(
                    requireContext(),
                    viewModel.locationList,
                    viewModel.selectedLocationIdList,
                    this@RickAndMortyListFragment
                )
                binding.apply {
                    val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                    rvLocation.layoutManager = layoutManager
                    rvLocation.adapter = viewModel.locationsAdapter
                }
                viewModel.recyclerViewState?.let {
                    binding.rvLocation.getLayoutManager()?.onRestoreInstanceState(it)
                }

                fetchCharacters()
                checkLocationListScrolled()
            }
        }
        viewModel.allCharactersResponse.observe(viewLifecycleOwner) {
            it?.let {
                viewModel.charactersAdapter = CharactersAdapter(requireContext(), it, this@RickAndMortyListFragment)
                binding.apply {
                    val layoutManager =
                        LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                    rvCharacter.layoutManager = layoutManager
                    rvCharacter.adapter = viewModel.charactersAdapter
                }
            }
        }
        viewModel.multipleCharactersResponse.observe(viewLifecycleOwner) {
            it?.let {
                viewModel.charactersAdapter = CharactersAdapter(requireContext(), it,this@RickAndMortyListFragment)
                binding.apply {
                    val layoutManager =
                        LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                    rvCharacter.layoutManager = layoutManager
                    rvCharacter.adapter = viewModel.charactersAdapter
                }
            }
        }
    }

    fun fetchCharacters() {
        val formattedIdList = getFormattedIdList()
        if (formattedIdList.isEmpty()) {
            viewModel.getAllCharacters()
        } else {
            viewModel.getMultipleCharacters(formattedIdList)
        }
    }

    override fun onLocationClickListener(locationModel: LocationModel) {
        if (!viewModel.selectedLocationIdList.contains(locationModel.id)) {
            viewModel.selectedLocationIdList.add(locationModel.id)
        } else {
            viewModel.selectedLocationIdList.remove(locationModel.id)
        }
        for (i in 0 until locationModel.residents.size) {
            val characterId = getCharacterIdFromResidentUrl(locationModel.residents.get(i))
            if (!viewModel.residentIdList.contains(characterId)) {
                viewModel.residentIdList.add(characterId)
            } else {
                viewModel.residentIdList.remove(characterId)
            }
        }
        viewModel.locationsAdapter.notifyDataSetChanged()
        fetchCharacters()
    }

    private fun getCharacterIdFromResidentUrl(residentUrl: String): String {
        return residentUrl.substring(residentUrl.lastIndexOf("/") + 1)
    }

    private fun getFormattedIdList(): String {
        var formattedIdList = ""
        for (i in 0 until viewModel.residentIdList.size) {
            formattedIdList += viewModel.residentIdList.get(i) + ","
        }
        return formattedIdList
    }

    private fun getFormattedEpisodes(episodeList: MutableList<String>):String{
        var formattedStr = ""
        for(i in 0 until episodeList.size){
            if(i!=0){
                formattedStr += ", " + episodeList[i].substring(episodeList[i].lastIndexOf("/") +1)
            } else{
                formattedStr += episodeList[i].substring(episodeList[i].lastIndexOf("/") +1)
            }
        }
        return formattedStr
    }

    override fun onCharacterClickListener(characterModel: CharacterModel) {
        val bundle = Bundle()
        bundle.putString("name",characterModel.name)
        bundle.putString("image",characterModel.image)
        bundle.putString("status",characterModel.status)
        bundle.putString("species",characterModel.species)
        bundle.putString("gender",characterModel.gender)
        bundle.putString("origin",characterModel.origin.name)
        bundle.putString("location",characterModel.location.name)
        bundle.putString("episode",getFormattedEpisodes(characterModel.episode))
        bundle.putString("created",characterModel.created)
        view?.findNavController()?.navigate(R.id.action_rickAndMortyListFragment_to_characterDetailFragment,bundle)
    }
}