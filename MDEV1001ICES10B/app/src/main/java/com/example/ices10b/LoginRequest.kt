package com.example.ices10b
import com.google.gson.annotations.SerializedName
data class LoginRequest (
    @SerializedName("username")
    var username: String,
    @SerializedName("password")
    var password: String
)
