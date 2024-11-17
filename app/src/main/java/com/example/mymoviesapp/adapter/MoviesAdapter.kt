package com.example.mymoviesapp.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.core.domain.model.MoviesNow
import com.example.mymoviesapp.databinding.ItemMoviesTrendingBinding
import com.example.mymoviesapp.ui.DetailActivity

class MoviesAdapter :
    ListAdapter<MoviesNow, MoviesAdapter.MoviesViewHolder>(
        createDiffCallback(
            areItemsTheSame = { oldItem, newItem -> oldItem.id == newItem.id },
            areContentsTheSame = { oldItem, newItem -> oldItem.id == newItem.id }
        )) {

    private val baseImageUrl = "https://image.tmdb.org/t/p/w500"

    private var centerPosition = -1
    private var previousCenterPosition = -1

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MoviesViewHolder {
        val binding =
            ItemMoviesTrendingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MoviesViewHolder(binding)
    }

    inner class MoviesViewHolder(val binding: ItemMoviesTrendingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movies: MoviesNow, isCenter: Boolean) {
            val fullPosterUrl = baseImageUrl + movies.posterPath
            Glide.with(itemView.context)
                .load(fullPosterUrl)
                .into(binding.ivMoviesTrending)
            // Update visibility based on center position
            binding.tvItemTitle.visibility = if (isCenter) View.VISIBLE else View.GONE
            binding.viewLayout.visibility = if (isCenter) View.VISIBLE else View.GONE

            binding.viewLayout.setOnClickListener {
                val id = movies.id
                val intent = Intent(itemView.context, DetailActivity::class.java).apply {
                    putExtra(DetailActivity.ID_MOVIES, id)
                }
                itemView.context.startActivity(intent)
            }
        }
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        val listMovies = getItem(position)
        holder.bind(listMovies, position == centerPosition)
    }

    fun updateCenterPosition(newPosition: Int) {
        if (newPosition != centerPosition) {
            previousCenterPosition = centerPosition
            centerPosition = newPosition

            previousCenterPosition.takeIf { it >= 0 }?.let { notifyItemChanged(it) }
            notifyItemChanged(centerPosition)
        }
    }

}