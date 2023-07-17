package com.example.ices8b

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.IOException

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    var adapter = MovieAdapter(emptyList())
    private lateinit var btnAdd: Button
    companion object {
        private const val ADD_EDIT_REQUEST_CODE = 1
    }
    object RetrofitClient {
        private const val BASE_URL = "https://mdev1001-m2023-api.onrender.com/api/"

        private val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        val movieApiService: MovieApiService = retrofit.create(MovieApiService::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnAdd = findViewById(R.id.btnAdd)
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        btnAdd.setOnClickListener {
            val intent = Intent(this, addmovie::class.java)
            startActivityForResult(intent, ADD_EDIT_REQUEST_CODE)
        }
        recyclerView.adapter = adapter
        adapter.movieUpdateCallback = {movie->
            val intent = Intent(this, addmovie::class.java)
            intent.putExtra("movie", movie)
            startActivityForResult(intent, ADD_EDIT_REQUEST_CODE)

        }
        fetchMovies()
        onDeleteMovie()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ADD_EDIT_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // Fetch the updated movie list and update the adapter
            fetchMovies()
        }
    }

    private fun fetchMovies() {
        val url = "https://mdev1001-m2023-api.onrender.com/api/list"

        val client = OkHttpClient()

        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                displayErrorMessage("Network error occurred.")
            }

            override fun onResponse(call: Call, response: okhttp3.Response) {
                val responseData = response.body()?.string()
                if (response.isSuccessful && !responseData.isNullOrEmpty()) {
                    val movieListType = object : TypeToken<List<Movie>>() {}.type
                    val movies = Gson().fromJson<List<Movie>>(responseData, movieListType)
                    runOnUiThread {
                        if (movies.isNotEmpty()) {
                            adapter.movies = movies
                            adapter.notifyDataSetChanged()
                        } else {
                            displayErrorMessage("No movies available.")
                        }
                    }
                } else {
                    displayErrorMessage("Server returned an error.")
                }
            }
        })
    }

    private fun displayErrorMessage(message: String) {
        runOnUiThread {
            AlertDialog.Builder(this)
                .setTitle("Error")
                .setMessage(message)
                .setPositiveButton("OK", null)
                .show()
        }
    }

    private fun onDeleteMovie() {
        adapter.onDeleteClickListener = { movie ->
            val dialog = AlertDialog.Builder(this)
                .setTitle("Delete Movie")
                .setMessage("Are you sure you want to delete this movie?")
                .setPositiveButton("Delete") { _, _ ->
                    deleteMovie(movie)
                }
                .setNegativeButton("Cancel", null)
                .create()
            dialog.show()

        }
    }
    private fun deleteMovie(movie: Movie) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val response = RetrofitClient.movieApiService.deleteMovie(movie._id)
                if (response.isSuccessful) {
                    adapter.removeMovie(movie) // Call removeMovie in the adapter
                } else {
                    displayErrorMessage("Failed to delete movie. Error code: ${response.code()}")
                }
            } catch (e: Exception) {
                if (e is HttpException) {
                    displayErrorMessage("Network error: ${e.message()}")
                } else {
                    displayErrorMessage("Error: ${e.message}")
                }
            }
        }
    }
}