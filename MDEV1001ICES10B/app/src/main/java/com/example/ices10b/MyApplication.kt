package com.example.ices10b

import android.app.Application
import com.example.ices10b.UserApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class MyApplication : Application() {
    lateinit var movieApiService: UserApi

    override fun onCreate() {
        super.onCreate()

        val moshi = Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://mdev1001-m2023-api.onrender.com/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

        movieApiService = retrofit.create(UserApi::class.java)
    }
}