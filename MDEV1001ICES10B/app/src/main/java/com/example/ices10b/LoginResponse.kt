package com.example.ices10b
import com.google.gson.annotations.SerializedName
data class LoginResponse (
    @SerializedName("success")
    val success: Boolean?=null,
    @SerializedName("token")
    val token: String?=null,
    @SerializedName("msg")
    val msg: String?=null
)