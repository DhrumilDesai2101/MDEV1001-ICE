package com.example.ices10b

import com.example.ices10b.ApiClient
import com.example.ices10b.LoginRequest
import com.example.ices10b.RegisterRequest
import com.example.ices10b.LoginResponse
import com.example.ices10b.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.Path
interface UserApi {
    @POST("/api/login")
    suspend fun loginUser(@Body loginRequest: LoginRequest): Response<LoginResponse>
    @POST("/api/register")
    suspend fun registerUser(@Body registerRequest: RegisterRequest): Response<RegisterResponse>

    @DELETE("delete/{id}")
    suspend fun deleteMovie(@Path("id") movieId: String): Response<Void>

    companion object {
        fun getApi(): UserApi? {
            return ApiClient.client?.create(UserApi::class.java)
        }
    }
}