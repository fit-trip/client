package com.example.fittrip.schedule.adapter

import android.content.Intent
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fittrip.ActivityMain
import com.example.fittrip.TokenManager
import com.example.fittrip.databinding.ItemSharedScheduleBinding
import com.example.fittrip.schedule.CopyRequest
import com.example.fittrip.schedule.ScheduleApi
import com.example.fittrip.schedule.ScheduleResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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

        binding.imgCopy.setOnClickListener {
            val retrofit = createDefaultRetrofit(ScheduleApi.BASE_URL)
            val service = retrofit.create(ScheduleApi::class.java)
            Log.d("psh", "datas[position].id: ${datas[position].id}")
            service.copySchedule(TokenManager.token, CopyRequest(datas[position].id)).enqueue(
                object : retrofit2.Callback<Unit> {
                    override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                        if (response.isSuccessful) {
                            val intent = Intent(it.context, ActivityMain::class.java)
                            intent.putExtra("FragmentName", "mySchedule")
                            it.context.startActivity(intent)
                        }
                    }

                    override fun onFailure(call: Call<Unit>, t: Throwable) {
                        TODO("Not yet implemented")
                    }
                })
        }
        binding.textUsername.text = datas[position].ownerId
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

    private fun createDefaultRetrofit(baseUrl: String) = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}