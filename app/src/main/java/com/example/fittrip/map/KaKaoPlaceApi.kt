package com.example.fittrip.map

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface KaKaoPlaceApi {
    companion object {
        const val BASE_URL = "https://dapi.kakao.com"
        const val REST_API_KEY = "1f5fc33109892628ac4b9609c276f2d6"
    }

    @GET("/v2/local/search/category.json")
    fun getPlace(
        @Header("Authorization") key: String,
        @Query("x") x: String,
        @Query("y") y: String,
        @Query("radius") radius: Int,
        @Query("category_group_code") category: String = "AT4"
    ): retrofit2.Call<KaKaoPlaceResponse>
}

data class KaKaoPlaceResponse(
    val documents: List<Document>
)

data class Document(
    var place_name: String,
    var address_name: String,
    var road_address_name: String,
    var x: String,
    var y: String
)
