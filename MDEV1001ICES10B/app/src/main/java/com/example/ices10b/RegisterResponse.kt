package com.example.ices10b
import com.google.gson.annotations.SerializedName
data class RegisterResponse (
    @SerializedName("success")
    var success: Boolean ?= null,
    @SerializedName("msg")
    var msg:String?=null
)