package com.example.fittrip.schedule.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.fittrip.databinding.ItemSharedRouteBinding

class SharedRouteViewHolder(val binding: ItemSharedRouteBinding)
    : RecyclerView.ViewHolder(binding.root)

class SharedRouteAdapter(val datas: List<String>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    lateinit var binding: ItemSharedRouteBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder
            = SharedRouteViewHolder(ItemSharedRouteBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        binding = (holder as SharedRouteViewHolder).binding
        binding.textRouteName.text = datas[position]
    }

    override fun getItemCount(): Int = datas.size
}