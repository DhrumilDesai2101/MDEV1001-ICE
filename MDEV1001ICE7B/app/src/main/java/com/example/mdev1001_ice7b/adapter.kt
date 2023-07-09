package com.example.mdev1001_ice7b

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.mdev1001_ice7b.R
import com.example.mdev1001_ice7b.Movie

class adapter(var movies: List<Movie>) : RecyclerView.Adapter<adapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.title)
        val studioTextView: TextView = itemView.findViewById(R.id.studio)
        val ratingTextView: TextView = itemView.findViewById(R.id.rating)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.movie_list, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = movies[position]
        holder.titleTextView.text = movie.title
        holder.studioTextView.text = movie.studio
        holder.ratingTextView.text = movie.criticsRating.toString()

        // Set the background color of ratingTextView based on the rating
        val rating = movie.criticsRating
        val context = holder.itemView.context

        val color = when {
            rating > 7 -> ContextCompat.getColor(context, R.color.green)
            rating > 5 -> ContextCompat.getColor(context, R.color.blue)
            else -> ContextCompat.getColor(context, R.color.red)
        }

        holder.ratingTextView.setBackgroundColor(color)
    }

    override fun getItemCount(): Int {
        return movies.size
    }
}