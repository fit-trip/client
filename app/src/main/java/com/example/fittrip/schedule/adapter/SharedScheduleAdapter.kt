package com.example.fittrip.schedule.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fittrip.databinding.ItemSharedScheduleBinding
import com.example.fittrip.schedule.ScheduleResponse

class SharedScheduleViewHolder(val binding: ItemSharedScheduleBinding)
    : RecyclerView.ViewHolder(binding.root)

class SharedScheduleAdapter(val datas: MutableList<ScheduleResponse>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    lateinit var binding: ItemSharedScheduleBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder
            = SharedScheduleViewHolder(ItemSharedScheduleBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        binding = (holder as SharedScheduleViewHolder).binding

        val recyclerView = binding.recyclerviewScheduleDetail
        recyclerView.adapter = SharedRouteAdapter(datas[position].locationsName)
        recyclerView.layoutManager = LinearLayoutManager(binding.root.context)

        binding.textScheduleDesc.text = datas[position].description
        binding.textCost.text = "${convertMilliSecToMinute(datas[position].totalDuration)}분|${roundUpToNearestHundred(datas[position].totalFare)}원"
    }

    override fun getItemCount(): Int = datas.size

    private fun roundUpToNearestHundred(num: Int): Int {
        return num / 100 * 100
    }

    //밀리세컨드를 분으로 바꿔주는 메소드
    private fun convertMilliSecToMinute(milliSec: Int): Int {
        return milliSec / 1000 / 60
    }
}