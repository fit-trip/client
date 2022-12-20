package com.example.fittrip

import com.google.gson.annotations.SerializedName

data class UserDTO(
    @SerializedName("ID")
    val id: String,

    @SerializedName("NAME")
    val name: String,

    @SerializedName("PASSWORD")
    val password: String

    // @SerializedName으로 일치시켜 주지않을 경우엔 클래스 변수명이 일치해야함
    // @SerializedName()로 변수명을 입치시켜주면 클래스 변수명이 달라도 알아서 매핑
)