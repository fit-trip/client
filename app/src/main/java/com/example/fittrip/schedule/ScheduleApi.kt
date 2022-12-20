package com.example.fittrip.schedule

import com.example.fittrip.map.dto.LocationDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface ScheduleApi {
    companion object {
//        const val BASE_URL = "http://10.0.2.2:8080"
        const val BASE_URL = "http://172.20.39.161:8080"
    }

    @GET("/api/v1/schedules")
    fun getMySchedule(
        @Header("Authorization") token: String,
    ): retrofit2.Call<MutableList<ScheduleResponse>>

    @POST("/api/v1/schedules")
    fun createSchedule(
        @Header("Authorization") token: String,
        @Body req: CreateScheduleRequest
    ): retrofit2.Call<Unit>

    // ### Schedule Share ###

    @PUT("/api/v1/schedules")
    fun changeShareStatus(
        @Header("Authorization") token: String,
        @Body req: ShareScheduleRequest
    ): retrofit2.Call<Unit>

    @GET("/api/v1/schedules/shared")
    fun getAllSharedSchedule(): retrofit2.Call<List<ScheduleResponse>>
    // ### Schedule Detail ###

    @GET("/api/v1/route")
    fun getRouteInfoByScheduleId(
        @Header("Authorization") token: String,
        @Query("scheduleId") scheduleId: Int
    ): retrofit2.Call<RouteInfoResponseDto>
}

data class RouteInfoResponseDto(
    val duration: RouteInfoPerDurationDto,
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

data class ScheduleResponse(
    var id: Int,
    var name: String,
    var totalDuration: Int,
    var totalFare: Int,
    var sharedStatus: Boolean,
    var locationsName: List<String>,
    var isProgressed: Boolean = false,
    var description: String
)

data class CreateScheduleRequest(
    var locations: List<LocationDto>,
    var name: String = "새로운 일정"
)

data class ShareScheduleRequest(
    var scheduleId: Int,
    var sharedStatus: Boolean,
    var description: String
)