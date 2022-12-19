package com.example.fittrip.map

import android.util.Log
import com.example.fittrip.R
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class MapViewCommander(private val mapView: MapView) {
    companion object{
        val listedMarkerId: List<Int> = listOf(
            R.drawable.one_150,
            R.drawable.two_150,
            R.drawable.three_150
        )
    }

    fun setZoomLevel(zoomLevel: Int) {
        mapView.setZoomLevel(zoomLevel, true)
    }

    fun setMapPosition(x: Double, y: Double) {
        this.mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(x, y), true)
    }

    fun addMarker(marker: List<Marker>) {
        marker.forEach {
            val poiItem = MapPOIItem()
            poiItem.itemName = it.name
            poiItem.tag = it.tag
            poiItem.mapPoint = MapPoint.mapPointWithGeoCoord(it.y, it.x)
            poiItem.markerType = MapPOIItem.MarkerType.BluePin
            poiItem.selectedMarkerType = MapPOIItem.MarkerType.RedPin
            this.mapView.addPOIItem(poiItem)
        }
    }

    fun addListedMarker(marker: List<Marker>) {
        mapView.removeAllPOIItems()
        if (marker.size > 3) {
            //TODO("Marker 갯수 늘리기")
            return
        }

        for (i in marker.indices) {
            val poiItem = MapPOIItem()
            poiItem.itemName = marker[i].name

            poiItem.markerType = MapPOIItem.MarkerType.CustomImage
            poiItem.customImageResourceId = listedMarkerId[i]

            poiItem.tag = marker[i].tag
            poiItem.mapPoint = MapPoint.mapPointWithGeoCoord(marker[i].y, marker[i].x)
            this.mapView.addPOIItem(poiItem)
        }


    }

    fun loadMarker(x: Double?, y: Double?) {
        val retrofit = Retrofit.Builder()
            .baseUrl(KaKaoPlaceApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val api = retrofit.create(KaKaoPlaceApi::class.java)
        val call = api.getPlace("KakaoAK ${KaKaoPlaceApi.REST_API_KEY}", "$x", "$y", 300)

        call.enqueue(object : Callback<KaKaoPlaceResponse> {
            override fun onResponse(
                call: Call<KaKaoPlaceResponse>,
                response: Response<KaKaoPlaceResponse>
            ) {
                Log.d("psh", "${response.body()}")

                val placeList = response.body()?.documents
                val markers = ArrayList<MapViewCommander.Marker>();

                placeList?.forEach {
                    val marker = MapViewCommander.Marker(
                        it.place_name,
                        it.x.toDouble(),
                        it.y.toDouble(),
                        Objects.hash("${it.x}${it.y}")
                    )
                    markers.add(marker)
                }

                addMarker(markers)
            }
            override fun onFailure(call: Call<KaKaoPlaceResponse>, t: Throwable) {
                Log.d("psh", "onFailure")
            }
        })
    }

    data class Marker(val name: String, val x: Double, val y: Double, val tag: Int)
}