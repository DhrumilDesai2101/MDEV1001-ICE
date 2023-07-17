package com.example.ices8b

import com.example.ices8b.Movie
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieApiService {
  /*  @GET("api/list")
    suspend fun getMovies(): List<Movie>
*/
    @DELETE("delete/{id}")
    suspend fun deleteMovie(@Path("id") movieId: String): Response<Void>

}
