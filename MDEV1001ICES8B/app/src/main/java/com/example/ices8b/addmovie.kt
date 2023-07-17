package com.example.ices8b

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.ices8b.Movie
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import java.io.IOException
import java.net.HttpURLConnection
import java.util.UUID

class addmovie : AppCompatActivity() {
    private lateinit var movieIDEditText: EditText
    private lateinit var titleEditText: EditText
    private lateinit var studioEditText: EditText
    private lateinit var genresEditText: EditText
    private lateinit var directorsEditText: EditText
    private lateinit var writersEditText: EditText
    private lateinit var actorsEditText: EditText
    private lateinit var yearEditText: EditText
    private lateinit var lengthEditText: EditText
    private lateinit var descriptionEditText: EditText
    private lateinit var mpaRatingEditText: EditText
    private lateinit var criticsRatingEditText: EditText
    private lateinit var updateButton: Button
    private lateinit var cancelButton :Button
    private var movie: Movie? = null
    private var movieUpdateCallback: (() -> Unit)? = null
    private var movieApiService: MovieApiService? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.addmovie)

        movieIDEditText = findViewById(R.id.editMovieId)
        titleEditText = findViewById(R.id.editTitle)
        studioEditText = findViewById(R.id.editStudio)
        genresEditText = findViewById(R.id.editGenres)
        directorsEditText = findViewById(R.id.editDirectors)
        writersEditText = findViewById(R.id.editWriters)
        actorsEditText = findViewById(R.id.editActors)
        yearEditText = findViewById(R.id.editYear)
        lengthEditText = findViewById(R.id.editLength)
        descriptionEditText = findViewById(R.id.editDescription)
        mpaRatingEditText = findViewById(R.id.editMpaRating)
        criticsRatingEditText = findViewById(R.id.editCriticsRating)
        updateButton = findViewById(R.id.btnUpdate)
        cancelButton = findViewById(R.id.btnCancel)
        cancelButton.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
        val moshi = Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://mdev1001-m2023-api.onrender.com/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

        movieApiService = retrofit.create(MovieApiService::class.java)

        updateButton.setOnClickListener {
            updateMovie()
        }
        movie = intent.getParcelableExtra("movie")
        if (movie != null) {
            // Editing existing movie
            movieIDEditText.setText(movie!!.movieID)
            titleEditText.setText(movie!!.title)
            studioEditText.setText(movie!!.studio)
            genresEditText.setText(movie!!.genres.joinToString(", "))
            directorsEditText.setText(movie!!.directors.joinToString(", "))
            writersEditText.setText(movie!!.writers.joinToString(", "))
            actorsEditText.setText(movie!!.actors.joinToString(", "))
            lengthEditText.setText(movie!!.length.toString())
            yearEditText.setText(movie!!.year.toString())
            descriptionEditText.setText(movie!!.shortDescription)
            mpaRatingEditText.setText(movie!!.mpaRating)
            criticsRatingEditText.setText(movie!!.criticsRating.toString())

            updateButton.text = "Update"
        } else {
            updateButton.text = "Add"
        }
    }

    private fun updateMovie() {
        val movieID = movieIDEditText.text.toString()
        val title = titleEditText.text.toString()
        val studio = studioEditText.text.toString()
        val genres = genresEditText.text.toString().split(",").map { it.trim() }
        val directors = directorsEditText.text.toString().split(",").map { it.trim() }
        val writers = writersEditText.text.toString().split(",").map { it.trim() }
        val actors = actorsEditText.text.toString().split(",").map { it.trim() }
        val year = yearEditText.text.toString().toIntOrNull() ?: 0
        val length = lengthEditText.text.toString().toIntOrNull() ?: 0
        val description = descriptionEditText.text.toString()
        val mpaRating = mpaRatingEditText.text.toString()
        val criticsRating = criticsRatingEditText.text.toString().toDoubleOrNull() ?: 0.0

        val updatedMovie = Movie(
            _id = movie?._id ?: UUID.randomUUID().toString(),
            movieID = movieID,
            title = title,
            studio = studio,
            genres = genres,
            directors = directors,
            writers = writers,
            actors = actors,
            year = year,
            length = length,
            shortDescription = description,
            mpaRating = mpaRating,
            criticsRating = criticsRating
        )
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = if (movie != null) {
                    movieApiService?.updateMovie(movie!!._id, updatedMovie)
                } else {
                    movieApiService?.addMovie(updatedMovie)
                }

                if (response != null && response.code() == HttpURLConnection.HTTP_OK) {
                    withContext(Dispatchers.Main) {
                        displaySuccessMessage(if (movie != null) "Movie updated successfully." else "Movie added successfully.")
                    }
                    setResult(Activity.RESULT_OK)
                    finish()
                } else {
                    withContext(Dispatchers.Main) {
                        displayErrorMessage("Failed to update movie.")
                    }
                }
            } catch (e: IOException) {
                withContext(Dispatchers.Main) {
                    displayErrorMessage("Network error occurred.")
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    displayErrorMessage("Code updated.")
                }
            }
        }
    }
    private fun displayErrorMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
    private fun displaySuccessMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
    interface MovieApiService {

        @DELETE("api/delete/{id}")
        suspend fun deleteMovie(@Path("id") id: String)

        @POST("api/add")
        suspend fun addMovie(@Body movie: Movie): retrofit2.Response<Unit>

        @PUT("api/update/{_id}")
        suspend fun updateMovie(@Path("_id") _id: String, @Body movie: Movie): retrofit2.Response<Unit>
    }
}