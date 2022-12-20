package com.example.fittrip.schedule.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.widget.FrameLayout
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fittrip.TokenManager
import com.example.fittrip.databinding.ActivityDetailScheduleBinding
import com.example.fittrip.map.MapViewCommander
import com.example.fittrip.schedule.*
import com.example.fittrip.schedule.adapter.DetailScheduleAdapter
import net.daum.mf.map.api.MapView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DetailScheduleActivity : AppCompatActivity() {
    lateinit var binding: ActivityDetailScheduleBinding

    lateinit var mapViewCommander: MapViewCommander
    lateinit var mapView: MapView

    var markersPerFare = mutableListOf<MapViewCommander.Marker>()
    var markersPerDuration = mutableListOf<MapViewCommander.Marker>()

    var routesPerFare = mutableListOf<RouteInfo>()
    var routesPerDuration = mutableListOf<RouteInfo>()

    lateinit var recyclerView: androidx.recyclerview.widget.RecyclerView
    lateinit var durationInfoAdapter: DetailScheduleAdapter
    lateinit var fareInfoAdapter: DetailScheduleAdapter

    private lateinit var retrofit: Retrofit
    private lateinit var service: ScheduleApi

    private var scheduleId: Int = -1

    data class RouteInfo(val nextCost: Int?, val name: String, val status: RouteStatus)

    var status = RouteStatus.DURATION

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailScheduleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupMapViewForMapViewCommander()
        mapViewCommander.setMapPosition(37.5659771227203, 126.97787149449015)

        scheduleId = intent.getIntExtra("scheduleId", -1)

        if (scheduleId == -1) {
            //TODO("스케줄 아이디가 없습니다.")
        }

        setUpShareForm()

        retrofit = createDefaultRetrofit(ScheduleApi.BASE_URL)
        service = retrofit.create(ScheduleApi::class.java)

        scheduleId.let {
            val result = service.getRouteInfoByScheduleId(
                TokenManager.token,
                it
            )
            result.enqueue(getDetailScheduleCallback)
        }

        binding.btnStatus.setOnClickListener {
            when (status) {
                RouteStatus.DURATION -> {
                    binding.textRouteStatus.text = "소요 시간 최적 경로"
                    status = RouteStatus.FARE
                    mapViewCommander.addListedMarker(markersPerDuration)
                    recyclerView.adapter = durationInfoAdapter
                }

                RouteStatus.FARE -> {
                    binding.textRouteStatus.text = "소요 비용 최적 경로"
                    status = RouteStatus.DURATION
                    mapViewCommander.addListedMarker(markersPerFare)
                    recyclerView.adapter = fareInfoAdapter
                }
            }
        }

        mapViewCommander.setZoomLevel(5)
    }

    private fun setUpShareForm() {
        var isClickable = false

        fun openForm() {
            isClickable = true
            binding.backgroundBlur.visibility = FrameLayout.VISIBLE
            binding.layoutShareForm.visibility = FrameLayout.VISIBLE
        }
        fun closeForm() {
            isClickable = false
            binding.backgroundBlur.visibility = FrameLayout.INVISIBLE
            binding.layoutShareForm.visibility = FrameLayout.INVISIBLE
        }

        binding.backgroundBlur.setOnTouchListener(object : OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                Log.d("psh", "$isClickable")
                if (!isClickable) {
                    return false
                }
                return true
            }
        })

        binding.btnCloseForm.setOnClickListener {
            closeForm()
        }

        binding.btnShare.setOnClickListener {
            when (binding.layoutShareForm.visibility) {
                FrameLayout.VISIBLE -> {
                    closeForm()
                }
                FrameLayout.INVISIBLE -> {
                    openForm()
                }
            }
        }

        binding.btnShareReq.setOnClickListener {
            //TODO("공유 요청 시 간단 소개 내용 포함")
            val content = binding.editShareForm.text.toString()
            Log.d("psh", "$content")

            service.changeShareStatus(
                TokenManager.token,
                ShareScheduleRequest(scheduleId, true)
            ).enqueue(object : Callback<Unit> {
                override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
//                    TODO("Not yet implemented")
                }

                override fun onFailure(call: Call<Unit>, t: Throwable) {
//                    TODO("Not yet implemented")
                }
            })
        }
    }

    private val getDetailScheduleCallback = object: Callback<RouteInfoResponseDto> {
        override fun onResponse(
            call: Call<RouteInfoResponseDto>,
            response: Response<RouteInfoResponseDto>
        ) {
            if (response.isSuccessful) {
                val body = response.body()!!

                val routePerFare = body.fare
                val routePerDuration = body.duration

                //최적요금 정보 저장
                for (i in routePerFare.locations.indices) {
                    val location = routePerFare.locations[i]
                    val cost: Int? = routePerFare.fareForNextPlace[i]
                    val route = RouteInfo(cost, location.name!!, RouteStatus.FARE)

                    val marker = MapViewCommander.Marker(location.name!!, location.x!!, location.y!!, 0)
                    routesPerFare.add(route)
                    markersPerFare.add(marker)
                }

                //최적시간 정보 저장
                for (i in routePerDuration.locations.indices) {
                    val location = routePerDuration.locations[i]
                    val cost:Int? = routePerDuration.durationForNextPlace[i]
                    val route = RouteInfo(cost, location.name!!, RouteStatus.DURATION)

                    val marker = MapViewCommander.Marker(location.name!!, location.x!!, location.y!!, 0)
                    routesPerDuration.add(route)
                    markersPerDuration.add(marker)
                }


                durationInfoAdapter = DetailScheduleAdapter(routesPerDuration)
                fareInfoAdapter = DetailScheduleAdapter(routesPerFare)

                mapViewCommander.addListedMarker(markersPerFare)
                recyclerView = binding.detailScheduleRecyclerView
                recyclerView.adapter = fareInfoAdapter
                recyclerView.addItemDecoration(DividerItemDecoration(this@DetailScheduleActivity, LinearLayoutManager.VERTICAL))
                recyclerView.layoutManager = LinearLayoutManager(this@DetailScheduleActivity)
            }
        }

        override fun onFailure(
            call: Call<RouteInfoResponseDto>,
            t: Throwable
        ) {

        }

    }

    private fun setupMapViewForMapViewCommander() {
        mapView = MapView(this)
        mapViewCommander = MapViewCommander(mapView)

        val mapViewContainer = binding.mapView
        mapViewContainer.addView(mapView)
    }

    private fun createDefaultRetrofit(baseUrl: String) = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

enum class RouteStatus {
    DURATION,
    FARE
}