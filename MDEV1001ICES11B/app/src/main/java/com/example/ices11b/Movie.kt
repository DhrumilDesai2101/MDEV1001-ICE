package com.example.ices11b

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie (
    var documentID: String? = null,
    val _id: String,
    val movieID: Long,
    val title: String,
    val studio: String,
    val genres: List<String>,
    val directors: List<String>,
    val writers: List<String>,
    val actors: List<String>,
    val year: Int,
    val length: Int,
    val shortDescription: String,
    val mpaRating: String,
    val criticsRating: Double
):Parcelable