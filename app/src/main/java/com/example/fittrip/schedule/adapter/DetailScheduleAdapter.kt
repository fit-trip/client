package com.example.fittrip.schedule.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.fittrip.databinding.ItemDetailScheduleBinding
import com.example.fittrip.map.MapViewCommander


class DetailScheduleViewHolder(val binding: ItemDetailScheduleBinding)
    : RecyclerView.ViewHolder(binding.root)

class DetailScheduleAdapter(val datas: MutableList<Int>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    lateinit var binding: ItemDetailScheduleBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder
            = DetailScheduleViewHolder(ItemDetailScheduleBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        binding = (holder as DetailScheduleViewHolder).binding

        val optFare = 100
        val optDuration = 100
        binding.itemDetailScheduleImage.setImageResource(MapViewCommander.listedMarkerId[position])
        binding.itemDetailLocationName.text = "test$position"
    }

    override fun getItemCount(): Int = datas.size;
}