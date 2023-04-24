package com.sena.rickandmortyapp

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sena.rickandmortyapp.databinding.RowCharacterBinding

class CharactersAdapter(var context: Context, var characterList: MutableList<CharacterModel>,private val characterClickInterface: CharacterClickInterface) :
    RecyclerView.Adapter<CharactersAdapter.CharactersViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharactersViewHolder {
        val view = RowCharacterBinding.inflate(LayoutInflater.from(context) , parent,false)
        return CharactersViewHolder(view)
    }

    override fun onBindViewHolder(holder: CharactersViewHolder, position: Int)
    {
        val character = characterList.get(position)
        Glide.with(context)  //Library to place the image link from the api inside imageViewGame
            .load(character.image)
            .placeholder(R.drawable.empty_avatar)
            .into(holder.viewBinding.ivCharacter)
        holder.viewBinding.tvName.setText(character.name)
        val gender = character.gender.trim().lowercase()
        var genderImage : Int
        when(gender){
            "male" -> genderImage = R.drawable.male
            "female" -> genderImage = R.drawable.female
            else -> genderImage = R.drawable.other
        }
        holder.viewBinding.ivGender.setImageResource(genderImage)
        holder.viewBinding.characterRowLayout.setOnClickListener{
            characterClickInterface.onCharacterClickListener(character)
        }
    }

    inner class CharactersViewHolder(var viewBinding: RowCharacterBinding) : RecyclerView.ViewHolder(viewBinding.root) {
    }

    override fun getItemCount(): Int {
        return characterList.size
    }

}