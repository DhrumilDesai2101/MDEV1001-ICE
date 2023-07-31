package com.example.ices10b
import com.google.gson.annotations.SerializedName
data class RegisterRequest (
    @SerializedName("username")
    val username:String?=null,
    @SerializedName("EmailAddress")
    val EmailAddress:String?=null,
    @SerializedName("password")
    val password:String?=null,
    @SerializedName("FirstName")
    val FirstName:String?=null,
    @SerializedName("LastName")
    val LastName:String?=null,
)
