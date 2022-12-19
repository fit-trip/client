package com.example.fittrip.schedule

import com.example.fittrip.map.dto.LocationDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ScheduleApi {
    companion object {
        const val BASE_URL = "http://10.0.2.2:8080"
    }

    @GET("/api/v1/schedules")
    fun getMySchedule(
        @Header("Authorization") token: String,
    ): retrofit2.Call<MutableList<MyScheduleResponse>>

    @POST("/api/v1/schedules")
    fun createSchedule(
        @Header("Authorization") token: String,
        @Body req: CreateScheduleRequest
    ): retrofit2.Call<Unit>

    // ### Schedule Detail ###

    @GET("/api/v1/route")
    fun getRouteInfoByScheduleId(
        @Header("Authorization") token: String,
        scheduleId: Int
    ): retrofit2.Call<MutableList<RouteInfoResponseDto>>
}

data class RouteInfoResponseDto(
    val durations: RouteInfoPerDurationDto,
    val fare: RouteInfoPerFareDto
)

data class RouteInfoPerDurationDto(
    val locations: List<LocationDto>,
    val durationForNextPlace: List<Int>
)

data class RouteInfoPerFareDto(
    val locations: List<LocationDto>,
    val fareForNextPlace: List<Int>
)

data class MyScheduleResponse(
    var id: Int,
    var name: String,
    var totalDuration: Int,
    var totalFare: Int,
    var sharedStatus: Boolean,
    var locationsName: List<String>,
    var isProgressed: Boolean = false
)

data class CreateScheduleRequest(
    var locations: List<LocationDto>,
    var name: String = "새로운 일정"
)