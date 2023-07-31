package com.example.ices10b

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.ices10b.RegisterRequest
import com.example.ices10b.BaseResponse
import com.example.ices10b.RegisterResponse
import com.example.ices10b.UserRepository
import kotlinx.coroutines.launch
class RegisterViewModel (application: Application) : AndroidViewModel(application) {

    val userRepo = UserRepository()
    val registerResult: MutableLiveData<BaseResponse<RegisterResponse?>> = MutableLiveData()
    fun registerUser(username:String,FirstName:String,LastName:String,email: String, password: String) {

        registerResult.value = BaseResponse.Loading()
        viewModelScope.launch {
            try {
                val registerRequest = RegisterRequest(username,email,password,
                    FirstName, LastName
                )
                val response = userRepo.registerUser(registerRequest = registerRequest)
//                Log.d("ResponseCode", response?.code().toString())
                if (response?.code() == 200 || response?.code() == 201) {
                    registerResult.value = BaseResponse.Success(response.body())
                } else {
                    registerResult.value = BaseResponse.Error(response?.message())
                }
            } catch (ex: Exception) {
                registerResult.value = BaseResponse.Error(ex.message)
            }
        }
    }
}