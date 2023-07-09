package com.example.mdev1001_ice7b


import com.example.mdev1001_ice7b.Movie
import retrofit2.http.GET

interface apiservice {
    @GET("api/list")
    suspend fun getMovies(): List<Movie>
}