package com.example.mdev1001_ice7b

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mdev1001_ice7b.adapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.rv)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val adapter = adapter(emptyList())
        recyclerView.adapter = adapter

        val retrofit = (application as myapp).movieApiService

        // Make an API request using CoroutineScope
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val movies = retrofit.getMovies()
                if (movies.isEmpty()) {
                    displayErrorMessage("No movies available.")
                } else {
                    adapter.movies = movies
                    adapter.notifyDataSetChanged()
                }
            } catch (e: IOException) {
                displayErrorMessage("Network error occurred.")
            } catch (e: HttpException) {
                displayErrorMessage("Server returned an error.")
            }
        }
    }

    private fun displayErrorMessage(message: String) {
        Toast.makeText(this, "Error: $message", Toast.LENGTH_SHORT).show()
    }
}