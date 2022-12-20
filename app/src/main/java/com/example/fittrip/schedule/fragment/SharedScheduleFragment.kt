package com.example.fittrip.schedule.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fittrip.databinding.FragmentSharedScheduleBinding
import com.example.fittrip.schedule.ScheduleApi
import com.example.fittrip.schedule.ScheduleResponse
import com.example.fittrip.schedule.adapter.SharedScheduleAdapter
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class SharedScheduleFragment : Fragment() {

    lateinit var binding: FragmentSharedScheduleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        binding = FragmentSharedScheduleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerviewSharedSchedule.adapter
        val retrofit = createDefaultRetrofit(ScheduleApi.BASE_URL)
        val service = retrofit.create(ScheduleApi::class.java)
        service.getAllSharedSchedule().enqueue(object : retrofit2.Callback<List<ScheduleResponse>> {
            override fun onResponse(
                call: Call<List<ScheduleResponse>>,
                response: Response<List<ScheduleResponse>>
            ) {
                if (response.isSuccessful) {
                    val sharedSchedules = response.body()?.toMutableList()
                    sharedSchedules?.reverse()
                    binding.recyclerviewSharedSchedule.adapter =
                        SharedScheduleAdapter(sharedSchedules!!)
                    binding.recyclerviewSharedSchedule.layoutManager =
                        LinearLayoutManager(context)
                }
            }

            override fun onFailure(call: Call<List<ScheduleResponse>>, t: Throwable) {
//                TODO("Not yet implemented")
            }

        })
    }

    private fun createDefaultRetrofit(baseUrl: String) = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}