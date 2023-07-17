package com.example.ices8b

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.ices8b.R
import com.example.ices8b.Movie

class MovieAdapter(var movies: List<Movie>) : RecyclerView.Adapter<MovieAdapter.ViewHolder>(){
    var movieUpdateCallback: ((Movie) -> Unit)? = null
    var onDeleteClickListener: ((Movie) -> Unit)? = null

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.title)
        val studioTextView: TextView = itemView.findViewById(R.id.studio)
        val ratingTextView: TextView = itemView.findViewById(R.id.rating)
        val constraintLayout:ConstraintLayout = itemView.findViewById(R.id.constraintLayout)
        val buttonDelete:Button = itemView.findViewById(R.id.Delete)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.movieitem, parent, false)
        return ViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = movies[position]
        holder.titleTextView.text = movie.title
        holder.studioTextView.text = movie.studio
        holder.ratingTextView.text = movie.criticsRating.toString()
        holder.buttonDelete.setOnClickListener {
            onDeleteClickListener?.invoke(movie)
        }
        // Set the background color of ratingTextView based on the rating
        val rating = movie.criticsRating
        val context = holder.itemView.context
        val color = when {
            rating > 7 -> ContextCompat.getColor(context, R.color.green)
            rating > 5 -> ContextCompat.getColor(context, R.color.yellow)
            else -> ContextCompat.getColor(context, R.color.red)
        }
        holder.ratingTextView.setBackgroundColor(color)
        holder.constraintLayout.setOnClickListener {
            movieUpdateCallback?.invoke(movie)
        }

    }
    @SuppressLint("NotifyDataSetChanged")
    fun removeMovie(movie: Movie) {
        val updatedList = movies.toMutableList()
        updatedList.remove(movie)
        movies = updatedList.toList()
        notifyDataSetChanged()
    }
    override fun getItemCount(): Int {
        return movies.size
    }
}