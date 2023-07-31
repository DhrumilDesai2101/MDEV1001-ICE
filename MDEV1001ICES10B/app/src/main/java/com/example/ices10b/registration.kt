package com.example.ices10b

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import com.example.ices10b.BaseResponse
import com.example.ices10b.LoginResponse
import com.example.ices10b.RegisterResponse
import com.example.ices10b.SessionManager
import com.example.ices10b.LoginViewModel
import com.example.ices10b.RegisterViewModel

class registration : AppCompatActivity() {

        lateinit var editTextFirstname: EditText
        lateinit var editTextLastname: EditText
        lateinit var editTextEmail: EditText
        lateinit var editTextUsername: EditText
        lateinit var editTextPassword: EditText
        lateinit var editTextConfirmPassword: EditText
        lateinit var btnLogin : Button
        lateinit var btnRegister: Button
        private val viewModel by viewModels<RegisterViewModel>()

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_registration)
            editTextFirstname = findViewById(R.id.editTextFirstname)
            editTextLastname = findViewById(R.id.editTextLastname)
            editTextUsername = findViewById(R.id.editTextUsername)
            editTextEmail = findViewById(R.id.editTextEmail)
            editTextPassword = findViewById(R.id.editTextPassword)
            btnLogin = findViewById(R.id.btnLogin)
            btnRegister = findViewById(R.id.btnRegister)
            editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword)

            val token = SessionManager.getToken(this)
            if (!token.isNullOrBlank()) {
                navigateToHome()
            }
            viewModel.registerResult.observe(this) {
                when (it) {
                    is BaseResponse.Loading -> {
                        // showLoading()
                    }

                    is BaseResponse.Success -> {
                        //stopLoading()
                        SessionManager.saveBool(applicationContext, SessionManager.IS_LOGIN, true)
                        processRegister(it.data)
                    }

                    is BaseResponse.Error -> {
                        processError(it.msg)
                    }
                }
            }
            btnLogin.setOnClickListener {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }

            btnRegister.setOnClickListener {
                doRegister()
            }
        }

        private fun doRegister() {
            val firstname = editTextFirstname.text.toString()
            val lastname = editTextLastname.text.toString()
            val username = editTextUsername.text.toString()
            val email = editTextUsername.text.toString()
            val password = editTextPassword.text.toString()
            viewModel.registerUser(username,firstname,lastname,email, password)
        }

        private fun processError(msg: String?) {
            //  showToast("Error:$msg")
            if (msg != null) {
                Log.d("Error", msg)
            }
        }
        private fun processRegister(data: RegisterResponse?) {
            //showToast("Success:  ${data?.msg}")
            if (!data?.msg.isNullOrEmpty()) {
                navigateToHome()
            }
        }
        private fun showToast(msg: String) {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        }
        private fun navigateToHome() {
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
            startActivity(intent)
        }
}