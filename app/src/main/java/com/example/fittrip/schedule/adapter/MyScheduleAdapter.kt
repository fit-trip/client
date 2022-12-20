package com.example.fittrip.schedule.adapter

import android.content.Intent
import android.os.Build
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.fittrip.TokenManager
import com.example.fittrip.databinding.ItemMyScheduleBinding
import com.example.fittrip.map.dto.LocationDto
import com.example.fittrip.schedule.CreateScheduleRequest
import com.example.fittrip.schedule.ScheduleResponse
import com.example.fittrip.schedule.ScheduleApi
import com.example.fittrip.schedule.activity.DetailScheduleActivity
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDateTime

class MyScheduleViewHolder(val binding: ItemMyScheduleBinding)
    : RecyclerView.ViewHolder(binding.root)

class MyScheduleAdapter(val datas: MutableList<ScheduleResponse>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    lateinit var binding: ItemMyScheduleBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder
            = MyScheduleViewHolder(ItemMyScheduleBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        binding = (holder as MyScheduleViewHolder).binding

        binding.itemScheduleNameRoot.setOnClickListener {
            Intent(it.context, DetailScheduleActivity::class.java).apply {
                putExtra("scheduleId", datas[position].id)
                it.context.startActivity(this)
            }
        }

        if (!datas[position].isProgressed) {
            binding.itemScheduleNameRoot.removeAllViews()
            binding.itemScheduleNameRoot.addView(ProgressBar(binding.itemScheduleNameRoot.context))
        } else {
            val optFare = roundUpToNearestHundred(datas[position].totalFare)
            val optDuration = convertMilliSecToMinute(datas[position].totalDuration)

            binding.itemScheduleName.text = datas[position].name
            binding.itemScheduleOptCost.text = "최적 비용: $optFare 원"
            binding.itemScheduleOptDuration.text = "최적 시간: $optDuration 분"
            binding.itemSchedulePreviewLocations.text = datas[position].locationsName.joinToString(", ")
        }
//        TODO("Date Time 수정 필요")
        binding.itemScheduleCreatedDate.text = """
            생성일: ${LocalDateTime.now().format(
            java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd")
        )}
        """.trimIndent()
    }

    override fun getItemCount(): Int = datas.size;

    fun refreshMySchedule(complete: Boolean = true) {
        val retrofit = createDefaultRetrofit(ScheduleApi.BASE_URL)
        val api = retrofit.create(ScheduleApi::class.java)
        val call = api.getMySchedule(TokenManager.token)

        call.enqueue(object : retrofit2.Callback<MutableList<ScheduleResponse>> {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onResponse(call: Call<MutableList<ScheduleResponse>>,
                                    response: Response<MutableList<ScheduleResponse>>) {
                val mySchedules = response.body()
                if (mySchedules == null) {
                    Log.d("MyScheduleAdapter", "응답이 없습니다.")
                    return
                }

                refreshCompleteSchedule(mySchedules)

                if (!complete) {
                    Log.d("psh", "${LocalDateTime.now()}")
                    Log.d("psh", "inComplete!")
                    datas.add(0, inCompletedSchedule())
                }
//                 TODO("가끔 화면에 로딩 중 표시가 안 뜨는 경우가 있음")
                notifyDataSetChanged()
                Thread.sleep(200)
            }

            private fun inCompletedSchedule() = ScheduleResponse(
                id = 0,
                name = "새로운 일정",
                totalFare = 0,
                sharedStatus = false,
                totalDuration = 0,
                locationsName = listOf(),
                description = "",
                ownerId = ""
            )

            private fun refreshCompleteSchedule(mySchedules: MutableList<ScheduleResponse>) {
                mySchedules.map { it.isProgressed = true }
                datas.clear()
                datas.addAll(mySchedules)
                datas.reverse()
            }

            override fun onFailure(call: Call<MutableList<ScheduleResponse>>, t: Throwable) {}
        })
    }

    fun addAndRefreshMySchedule(selectedPlaces: Array<Parcelable>) {
        refreshMySchedule(false)

        val retrofit = createDefaultRetrofit(ScheduleApi.BASE_URL)
        val api = retrofit.create(ScheduleApi::class.java)
        val scheduleRequest = CreateScheduleRequest(selectedPlaces.map { it as LocationDto })
        val call = api.createSchedule(TokenManager.token, scheduleRequest)


        call.enqueue(object : retrofit2.Callback<Unit>{
            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                refreshMySchedule()
            }

            override fun onFailure(call: Call<Unit>, t: Throwable) {
//                TODO("Not yet implemented")
            }
        })
    }

    private fun createDefaultRetrofit(baseUrl: String) = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private fun roundUpToNearestHundred(num: Int): Int {
        return num / 100 * 100
    }

    //밀리세컨드를 분으로 바꿔주는 메소드
    private fun convertMilliSecToMinute(milliSec: Int): Int {
        return milliSec / 1000 / 60
    }
}