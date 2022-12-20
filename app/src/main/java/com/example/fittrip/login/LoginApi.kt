package com.example.fittrip.login

import com.example.fittrip.BuildConfig
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApi {
    companion object {
        const val BASE_URL = BuildConfig.SERVER_URL
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
