package com.example.fittrip.login

import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApi {
    companion object {
        //        const val BASE_URL = "http://10.0.2.2:8080"
        const val BASE_URL = "http://172.20.39.161:8080"
    }

    @POST("/api/v1/auth")
    fun login(
        @Body req: LoginRequestDto
    ): retrofit2.Call<LoginResponseDto>
}

data class LoginResponseDto(
    val accessToken: String
)

data class LoginRequestDto(
    val id: String,
    val name: String = "test",
    val password: String
)
