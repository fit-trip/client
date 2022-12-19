package com.example.fittrip.schedule.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fittrip.TokenManager
import com.example.fittrip.databinding.ActivityDetailScheduleBinding
import com.example.fittrip.map.MapViewCommander
import com.example.fittrip.schedule.RouteInfoResponseDto
import com.example.fittrip.schedule.ScheduleApi
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailScheduleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupMapViewForMapViewCommander()
        mapViewCommander.setMapPosition(37.5659771227203, 126.97787149449015)

        savedInstanceState?.getInt("scheduleId")?.let {
            val retrofit = createDefaultRetrofit(ScheduleApi.BASE_URL)
            val service = retrofit.create(ScheduleApi::class.java)
            val result = service.getRouteInfoByScheduleId(
                TokenManager.token,
                it
            )

            result.enqueue(object: Callback<MutableList<RouteInfoResponseDto>> {
                override fun onResponse(
                    call: Call<MutableList<RouteInfoResponseDto>>,
                    response: Response<MutableList<RouteInfoResponseDto>>
                ) {
                    if (response.isSuccessful) {
                        val body = response.body()!!
                    }
                }

                override fun onFailure(
                    call: Call<MutableList<RouteInfoResponseDto>>,
                    t: Throwable
                ) {

                }

            })
        }

        val markers = listOf(
            MapViewCommander.Marker("test", 126.9778714944901,37.5659771227203 , 0),
            MapViewCommander.Marker("test",  126.9667714944901, 37.5659771227203, 0),
            MapViewCommander.Marker("test",  126.9778714944901, 37.5548771227203, 0)
        )
        mapViewCommander.addListedMarker(markers)
        mapViewCommander.setZoomLevel(5)

        val recyclerView = binding.detailScheduleRecyclerView
        recyclerView.adapter = DetailScheduleAdapter(mutableListOf(1, 2, 3))
        recyclerView.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
        recyclerView.layoutManager = LinearLayoutManager(this)
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