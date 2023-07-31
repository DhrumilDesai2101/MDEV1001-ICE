package com.example.ices10b
import com.example.ices10b.UserApi
import com.example.ices10b.LoginRequest
import com.example.ices10b.RegisterRequest
import com.example.ices10b.LoginResponse
import com.example.ices10b.RegisterResponse
import retrofit2.Response

class UserRepository {
    suspend fun loginUser(loginRequest: LoginRequest): Response<LoginResponse>? {
        return  UserApi.getApi()?.loginUser(loginRequest = loginRequest)
    }
    suspend fun registerUser(registerRequest: RegisterRequest): Response<RegisterResponse>?{
        return UserApi.getApi()?.registerUser(registerRequest = registerRequest)
    }
}