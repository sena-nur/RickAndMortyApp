package com.sena.rickandmortyapp

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.sena.rickandmortyapp.databinding.RowLocationBinding

class LocationsAdapter(var context: Context,
                       var locationList: MutableList<LocationModel>,
                       var selectedLocationIdList: MutableList<Int>,
                       private val locationClickInterface: LocationClickInterface) :
    RecyclerView.Adapter<LocationsAdapter.LocationsViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationsViewHolder {
        val view = RowLocationBinding.inflate(LayoutInflater.from(context) , parent,false)
        return LocationsViewHolder(view)
    }

    override fun onBindViewHolder(holder: LocationsViewHolder, position: Int)
    {
        val location = locationList.get(position)
        holder.viewBinding.tvLocation.setText(location.name)
        if(!selectedLocationIdList.contains(location.id)){
            holder.viewBinding.locationRowLayout.setBackgroundResource(R.drawable.panel_background)
            holder.viewBinding.tvLocation.setTextColor(ContextCompat.getColor(context, R.color.purple_500))
        } else{
            holder.viewBinding.locationRowLayout.setBackgroundResource(R.drawable.panel_background_selected)
            holder.viewBinding.tvLocation.setTextColor(ContextCompat.getColor(context, R.color.white))
        }
        holder.viewBinding.locationRowLayout.setOnClickListener{
            locationClickInterface.onLocationClickListener(location)
        }
    }

    inner class LocationsViewHolder(var viewBinding: RowLocationBinding) : RecyclerView.ViewHolder(viewBinding.root) {
    }

    override fun getItemCount(): Int {
        return locationList.size
    }

}