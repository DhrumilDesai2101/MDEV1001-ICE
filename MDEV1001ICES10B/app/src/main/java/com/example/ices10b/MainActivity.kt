package com.example.ices10b

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import com.example.ices10b.BaseResponse
import com.example.ices10b.LoginResponse
import com.example.ices10b.SessionManager
import com.example.ices10b.LoginViewModel

class MainActivity : AppCompatActivity() {
    lateinit var usernameEditText : EditText
    lateinit var passwordEditText: EditText
    lateinit var loginButton: Button
    lateinit var registerButton: Button
    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        usernameEditText = findViewById(R.id.usernameEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        loginButton = findViewById(R.id.loginButton)
        registerButton = findViewById(R.id.registerButton)
        val token = SessionManager.getToken(this)
        if (!token.isNullOrBlank()) {
            navigateToHome()
        }
        viewModel.loginResult.observe(this) {
            when (it) {
                is BaseResponse.Loading -> {
                    // showLoading()
                }

                is BaseResponse.Success -> {
                    //stopLoading()
                    SessionManager.saveBool(applicationContext,SessionManager.IS_LOGIN, true)
                    processLogin(it.data)
                }

                is BaseResponse.Error -> {
                    processError(it.msg)
                }

            }
        }
        registerButton.setOnClickListener {
            val intent = Intent(this, registration::class.java)
            startActivity(intent)
        }

        loginButton.setOnClickListener {
            doLogin()
        }
    }
    private fun doLogin(){
        val email = usernameEditText.text.toString()
        val password = passwordEditText.text.toString()
        viewModel.loginUser(email, password)
    }
    private fun processError(msg: String?) {
        //     showToast("Error:$msg")
        if(msg != null){
            Log.d("Error", msg)
        }
    }
    private fun processLogin(data: LoginResponse?) {
        //       showToast("Success: ${data?.msg}")
        if (!data?.msg.isNullOrEmpty()) {
            navigateToHome()
        }
    }
    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
    private fun navigateToHome() {
        val intent = Intent(this, movielist::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
        startActivity(intent)
    }
}