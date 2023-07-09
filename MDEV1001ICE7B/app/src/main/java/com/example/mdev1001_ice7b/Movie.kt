package com.example.mdev1001_ice7b

data class Movie(
    val movieID: String,
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
)
