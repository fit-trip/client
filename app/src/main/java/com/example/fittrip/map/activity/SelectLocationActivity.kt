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
import com.example.fittrip.ActivityMain
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
    companion object{
        var selectedPlaces = mutableListOf<LocationDto>()
    }
    lateinit var binding: ActivitySelectLocationBinding
    lateinit var adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>

    lateinit var mapViewCommander: MapViewCommander
    lateinit var mapView: MapView



    lateinit var scheduleName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        intent.getStringExtra("scheduleName")?.let {
            scheduleName = it
        }
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

        val lat = intent?.getDoubleExtra("lat", 37.5659771227203)
        val lng = intent?.getDoubleExtra("lng", 126.97787149449015)
        mapViewCommander.setMapPosition(lat!!, lng!!)
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
        val recyclerView = binding.selectLocationRecyclerView
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
                if (selectedPlaces.size == 1) {
                    Toast.makeText(this, "일정은 2개 이상 생성해주세요", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "일정 생성", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, ActivityMain::class.java)
                    intent.putExtra("scheduleName", scheduleName)
                    intent.putExtra("selectedPlaces", selectedPlaces.toTypedArray())
                    finish()
                    startActivity(intent)

//                val intent = Intent(this, MyScheduleActivity::class.java)
//                intent.putExtra("selectedPlaces", selectedPlaces.toTypedArray())
//                startActivity(intent)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * POIItemEventListener
     */
    override fun onPOIItemSelected(p0: MapView?, p1: MapPOIItem?) {
        if (selectedPlaces.size >= 5) {
            Toast.makeText(this, "관광지는 5개까지 선택 가능합니다.", Toast.LENGTH_SHORT).show()
            return
        }
        val latitude = p1?.mapPoint?.mapPointGeoCoord?.longitude
        val longitude = p1?.mapPoint?.mapPointGeoCoord?.latitude
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
        val y = p1?.mapPointGeoCoord?.latitude
        val x = p1?.mapPointGeoCoord?.longitude

        mapViewCommander.loadMarker(x, y)
    }


}



