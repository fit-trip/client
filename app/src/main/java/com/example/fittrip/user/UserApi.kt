package com.example.fittrip.user

import com.example.fittrip.login.LoginRequestDto
import com.example.fittrip.login.LoginResponseDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UserApi {
    companion object {
        const val BASE_URL = "http://10.0.2.2:8080"
    }

    @POST("/api/v1/user")
    fun register(
        @Body req: UserInfoDto
    ): retrofit2.Call<Unit>

    @GET("/api/v1/user")
    fun getUserInfo(
        @Body req: UserInfoRequestDto
    ): retrofit2.Call<UserInfoDto>
}


data class UserInfoDto (
    val id: String,
    val name: String,
    val password: String
)

data class UserInfoRequestDto (
    val userId: String
)
