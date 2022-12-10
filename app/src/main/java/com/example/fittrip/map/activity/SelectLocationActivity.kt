package com.example.fittrip.map.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fittrip.R
import com.example.fittrip.databinding.ActivitySelectLocationBinding
import com.example.fittrip.map.MapViewCommander
import com.example.fittrip.map.adapter.SelectedLocationAdapter
import com.example.fittrip.map.dto.LocationDto
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView
import net.daum.mf.map.api.MapView.MapViewEventListener
import net.daum.mf.map.api.MapView.POIItemEventListener

class SelectLocationActivity : AppCompatActivity(),
    POIItemEventListener, MapViewEventListener {
    lateinit var binding: ActivitySelectLocationBinding
    lateinit var adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>

    lateinit var mapViewCommander: MapViewCommander
    lateinit var mapView: MapView

    private val selectedPlaces = mutableListOf<LocationDto>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        툴바
        setSupportActionBar(binding.mapToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "관광지를 선택해 주세요"

//        RecyclerView 셋팅
        adapter = SelectedLocationAdapter(selectedPlaces)
        setAdaptorOnRecyclerView(adapter)

//        MapView 초기화 및 Commander 주입
        setupMapViewForMapViewCommander()
        intent?.let {
            val lat = it.getDoubleExtra("lat", 37.565526041255616)
            val lng = it.getDoubleExtra("lng", 126.97495136198495)
            mapViewCommander.setMapPosition(lat, lng)
        }
    }

    private fun setupMapViewForMapViewCommander() {
        mapView = MapView(this)
        mapView.setPOIItemEventListener(this)
        mapView.setMapViewEventListener(this)
        mapViewCommander = MapViewCommander(mapView)

        val mapViewContainer = binding.mapView
        mapViewContainer.addView(mapView)
    }

    private fun setAdaptorOnRecyclerView(adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>) {
        val recyclerView = binding.mapScheduleSelectedPlaceRecyclerView
        recyclerView.adapter = adapter
        val linearLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = linearLayoutManager
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.select_location, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.create_schedule -> {
                Toast.makeText(this, "일정 생성", Toast.LENGTH_SHORT).show()
                for (selectedPlace in selectedPlaces) {
                    selectedPlace.name
                    selectedPlace.x
                    selectedPlace.y
                }
//                TODO("일정 생성 호출, 내 일정 뷰로 이동")
            }
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * POIItemEventListener
     */
    override fun onPOIItemSelected(p0: MapView?, p1: MapPOIItem?) {
        val latitude = p1?.mapPoint?.mapPointGeoCoord?.latitude
        val longitude = p1?.mapPoint?.mapPointGeoCoord?.longitude
        val name = p1?.itemName

        val location = LocationDto(latitude, longitude, name)

        (adapter as SelectedLocationAdapter).addSelectedPlace(location)
    }

    override fun onCalloutBalloonOfPOIItemTouched(p0: MapView?, p1: MapPOIItem?) {
//        TODO("Not yet implemented")
    }

    override fun onCalloutBalloonOfPOIItemTouched(
        p0: MapView?,
        p1: MapPOIItem?,
        p2: MapPOIItem.CalloutBalloonButtonType?
    ) {
//        TODO("Not yet implemented")
    }

    override fun onDraggablePOIItemMoved(p0: MapView?, p1: MapPOIItem?, p2: MapPoint?) {
//        TODO("Not yet implemented")
    }


    /**
     * MapViewEventListener
     */
    override fun onMapViewInitialized(p0: MapView?) {
        Log.d("activity_select_loc", "onMapViewInitialized")
    }

    override fun onMapViewCenterPointMoved(p0: MapView?, p1: MapPoint?) {
        Log.d("activity_select_loc", "onMapViewCenterPointMoved")
    }

    override fun onMapViewZoomLevelChanged(p0: MapView?, p1: Int) {
        Log.d("activity_select_loc", "onMapViewZoomLevelChanged")
    }

    override fun onMapViewSingleTapped(p0: MapView?, p1: MapPoint?) {
        Log.d("activity_select_loc", "onMapViewSingleTapped")
    }

    override fun onMapViewDoubleTapped(p0: MapView?, p1: MapPoint?) {
        Log.d("activity_select_loc", "onMapViewDoubleTapped")
    }

    override fun onMapViewLongPressed(p0: MapView?, p1: MapPoint?) {
        Log.d("activity_select_loc", "onMapViewLongPressed")
    }

    override fun onMapViewDragStarted(p0: MapView?, p1: MapPoint?) {
        Log.d("activity_select_loc", "onMapViewDragStarted")
    }

    override fun onMapViewDragEnded(p0: MapView?, p1: MapPoint?) {
        Log.d("activity_select_loc", "onMapViewDragEnded")
    }

    override fun onMapViewMoveFinished(p0: MapView?, p1: MapPoint?) {
        Toast.makeText(this, "onMapViewMoveFinished", Toast.LENGTH_SHORT).show()
        Log.d("activity_select_loc", "ddd")

        val y = p1?.mapPointGeoCoord?.latitude
        val x = p1?.mapPointGeoCoord?.longitude

        mapViewCommander.loadMarker(x, y)
    }


}



