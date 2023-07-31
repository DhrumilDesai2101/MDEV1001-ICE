package com.example.ices10b
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.ices10b.LoginRequest
import com.example.ices10b.BaseResponse
import com.example.ices10b.LoginResponse
import com.example.ices10b.UserRepository
import kotlinx.coroutines.launch

class LoginViewModel (application: Application) : AndroidViewModel(application) {
    val userRepo = UserRepository()
    val loginResult: MutableLiveData<BaseResponse<LoginResponse?>> = MutableLiveData()

    fun loginUser(username: String, pwd: String) {

        loginResult.value = BaseResponse.Loading()
        viewModelScope.launch {
            try {
                val loginRequest = LoginRequest(
                    username = username,
                    password = pwd
                )
                val response = userRepo.loginUser(loginRequest = loginRequest)
//              Log.d("ResponseCode", response?.code().toString())
                if (response?.code() == 200) {
                    loginResult.value = BaseResponse.Success(response.body())
                } else {
                    loginResult.value = BaseResponse.Error(response?.message())
                }

            } catch (ex: Exception) {
                loginResult.value = BaseResponse.Error(ex.message)
            }
        }
    }
}