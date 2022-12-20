package com.example.fittrip

import GeoPoint
import GeoTrans
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fittrip.databinding.ActivitySearchBinding
import com.example.fittrip.map.NaverPlaceApi
import com.example.fittrip.map.NaverPlaceResponse
import com.example.fittrip.map.SearchResult
import com.example.fittrip.search.adapter.SearchResultAdapter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SearchActivity : AppCompatActivity() {

    var result: MutableList<SearchResult> = mutableListOf()
    lateinit var binding: ActivitySearchBinding

    fun searchPlace(query: String) {
        result.clear()

        val retrofit = Retrofit.Builder()
            .baseUrl(NaverPlaceApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val api = retrofit.create(NaverPlaceApi::class.java)

        val call = api.getPlace(
            NaverPlaceApi.CLIENT_ID,
            NaverPlaceApi.CLIENT_SECRET,
            query,
            start=1
        )

        call.enqueue(object : retrofit2.Callback<NaverPlaceResponse> {
            override fun onResponse(
                call: retrofit2.Call<NaverPlaceResponse>,
                response: retrofit2.Response<NaverPlaceResponse>
            ) {
                Log.d("SearchActivity", "response: ${response.body()}")

                val placeList = response.body()?.items

                // result 에 placeList 추가
                placeList?.forEach {
                    result.add(it)
                }

                // result를 순회하며 KETEC 좌표를 위도, 경도로 변환한 값을 저장
                for (i in 0 until result.size) {
                    Log.d("ryu", "mapx: ${result[i].mapx}")
                    Log.d("ryu", "mapy: ${result[i].mapy}")

                    var katec = GeoPoint(result[i].mapx.toDouble(), result[i].mapy.toDouble())
                    katec = GeoTrans.convert(GeoTrans.TM, GeoTrans.GEO, katec)
                    result[i].latitude = katec.y
                    result[i].longitude = katec.x
                }

                // result를 SearchActivity의 목록에 출력
                val recyclerView = binding.searchRecyclerView
                recyclerView.adapter = SearchResultAdapter(result)
                recyclerView.layoutManager = LinearLayoutManager(this@SearchActivity)
                recyclerView.addItemDecoration(DividerItemDecoration(this@SearchActivity, DividerItemDecoration.VERTICAL))
            }


            override fun onFailure(
                call: retrofit2.Call<NaverPlaceResponse>,
                t: Throwable
            ) {
                Log.d("SearchActivity", "error: $t")
            }
        })



    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)

        val search_view = binding.searchView
        search_view.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.d("검색어 입력", query.toString())
                searchPlace(query.toString())
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
//                Log.d("검색어 변경", newText.toString())
                return false
            }
        })


        setContentView(binding.root)
    }
}