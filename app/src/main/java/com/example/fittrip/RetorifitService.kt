package com.example.fittrip

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface RetorifitService {

    @GET("/api/v1/users/{userId}")
    fun getUser(): Call<UserDTO>

}