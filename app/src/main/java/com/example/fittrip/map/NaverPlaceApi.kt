package com.example.fittrip.map

import com.example.fittrip.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface NaverPlaceApi {
    companion object {
        const val BASE_URL = "https://openapi.naver.com"
        const val CLIENT_ID = BuildConfig.X_Naver_Client_Id
        const val CLIENT_SECRET = BuildConfig.X_Naver_Client_Secret
    }

    @GET("/v1/search/local.json")
    fun getPlace(
        @Header("X-Naver-Client-Id") id: String,
        @Header("X-Naver-Client-Secret") secret: String,
        @Query("query") query: String,
        @Query("display") display: Int = 5,
        @Query("start") start: Int,
        @Query("sort") random: String = "random",
    ): retrofit2.Call<NaverPlaceResponse>
}

data class NaverPlaceResponse(
    val lastBuildDate: String,
    val total: Int,
    val start: Int,
    val display: Int,
    val items: List<SearchResult>
)

data class SearchResult(
    var title: String,
    var link: String,
    var category: String,
    var description: String,
    var telephone: String,
    var address: String,
    var roadAddress: String,
    var mapx: Double,
    var mapy: Double,
    var longitude: Double,
    var latitude: Double
)
