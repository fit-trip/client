package com.example.fittrip.map.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fittrip.databinding.ItemSelectedLocationBinding
import com.example.fittrip.map.dto.LocationDto

class SelectedLocationViewHolder(val binding: ItemSelectedLocationBinding)
    : RecyclerView.ViewHolder(binding.root)

class SelectedLocationAdapter(val datas: MutableList<LocationDto>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val dataContext: MutableSet<LocationDto> = datas.toMutableSet()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder
            = SelectedLocationViewHolder(ItemSelectedLocationBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as SelectedLocationViewHolder).binding
        binding.itemData.text = datas[position].name
        binding.itemRoot.setOnClickListener {
            Log.d("psh", "${position}")
        }

        binding.itemBtn.setOnClickListener {
            removeSelectedPlace(position)
        }
    }

    override fun getItemCount(): Int = datas.size;

    fun removeSelectedPlace(selectedPlace: LocationDto) {
        dataContext.remove(selectedPlace)
        datas.clear()
        datas.addAll(dataContext)
        this.notifyDataSetChanged()
    }
    fun removeSelectedPlace(idx: Int) {
        dataContext.remove(datas[idx])
        datas.removeAt(idx)
        this.notifyDataSetChanged()
    }

    fun addSelectedPlace(selectedPlace: LocationDto) {
        dataContext.add(selectedPlace)
        datas.clear()
        datas.addAll(dataContext)
        this.notifyDataSetChanged()
    }
}