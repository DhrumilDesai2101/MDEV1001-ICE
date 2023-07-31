package com.example.ices10b
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Parcelize
data class Movie (
    @field:Json(name = "_id") val _id: String,
    @field:Json(name = "movieID") val movieID: String,
    @field:Json(name = "title") val title: String,
    @field:Json(name = "studio") val studio: String,
    @field:Json(name = "genres") val genres: List<String>,
    @field:Json(name = "directors") val directors: List<String>,
    @field:Json(name = "writers") val writers: List<String>,
    @field:Json(name = "actors") val actors: List<String>,
    @field:Json(name = "year") val year: Int,
    @field:Json(name = "length") val length: Int,
    @field:Json(name = "shortDescription") val shortDescription: String,
    @field:Json(name = "mpaRating") val mpaRating: String,
    @field:Json(name = "criticsRating") val criticsRating: Double
) : Parcelable