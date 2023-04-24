package com.sena.rickandmortyapp

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.sena.rickandmortyapp.databinding.FragmentCharacterDetailBinding

class CharacterDetailFragment : Fragment(R.layout.fragment_character_detail){
    private val viewModel: RickAndMortyListViewModel by viewModels()
    private var _binding: FragmentCharacterDetailBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCharacterDetailBinding.bind(view)
        binding.ivBack.setOnClickListener{
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
        setDetails()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setDetails(){
        Glide.with(requireContext())  //Library to place the image link from the api inside imageViewGame
            .load(arguments?.getString("image"))
            .placeholder(R.drawable.empty_avatar)
            .into(binding.ivCharacter)
        binding.characterName.text = arguments?.getString("name")
        binding.txtStatus.text = arguments?.getString("status")
        binding.txtSpecy.text = arguments?.getString("species")
        binding.txtGender.text = arguments?.getString("gender")
        binding.txtOrigin.text = arguments?.getString("origin")
        binding.txtLocation.text = arguments?.getString("location")
        binding.txtEpisodes.text = arguments?.getString("episode")
        binding.txtCreated.text = arguments?.getString("created")
        binding.characterName.text = arguments?.getString("name")
    }
}