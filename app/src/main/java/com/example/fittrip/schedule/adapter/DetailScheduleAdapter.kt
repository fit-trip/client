package com.example.fittrip.schedule.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.fittrip.databinding.ItemDetailScheduleBinding
import com.example.fittrip.map.MapViewCommander
import com.example.fittrip.schedule.activity.DetailScheduleActivity
import com.example.fittrip.schedule.activity.RouteStatus


class DetailScheduleViewHolder(val binding: ItemDetailScheduleBinding)
    : RecyclerView.ViewHolder(binding.root)

class DetailScheduleAdapter(val datas: MutableList<DetailScheduleActivity.RouteInfo>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    lateinit var binding: ItemDetailScheduleBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder
            = DetailScheduleViewHolder(ItemDetailScheduleBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        binding = (holder as DetailScheduleViewHolder).binding

        binding.itemDetailScheduleImage.setImageResource(MapViewCommander.listedMarkerId[position])
        binding.itemDetailLocationName.text = "${datas[position].name}"
        if (position != datas.size - 1) {
            when(datas[position].status) {
                RouteStatus.DURATION -> {
                    binding.itemDetailScheduleCostText.text = """
                        다음 소요 시간: ${convertMilliSecToMinute(datas[position].nextCost!!)} 분
                    """.trimIndent()
                }
                RouteStatus.FARE -> {
                    binding.itemDetailScheduleCostText.text = """
                        다음 소요 비용: ${roundUpToNearestHundred(datas[position].nextCost!!)} 원
                    """.trimIndent()
                }
            }

        } else {
            binding.itemDetailScheduleCostText.text = ""
        }

    }

    override fun getItemCount(): Int = datas.size;

    private fun roundUpToNearestHundred(num: Int): Int {
        return num / 10 * 10
    }

    //밀리세컨드를 분으로 바꿔주는 메소드
    private fun convertMilliSecToMinute(milliSec: Int): Int {
        return milliSec / 1000 / 60
    }
}