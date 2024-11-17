package com.example.mymoviesapp.favorite.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.core.domain.model.Movies
import com.example.mymoviesapp.favorite.databinding.ItemsFavoriteBinding
import com.example.mymoviesapp.ui.DetailActivity

class FavoriteAdapter :
    ListAdapter<Movies, FavoriteAdapter.FavoriteViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Movies>() {
            override fun areItemsTheSame(oldItem: Movies, newItem: Movies): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Movies, newItem: Movies): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding =
            ItemsFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val movie = getItem(position)
        if (movie != null) {
            holder.bind(movie)
        }
    }

    inner class FavoriteViewHolder(private val binding: ItemsFavoriteBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val baseImageUrl = "https://image.tmdb.org/t/p/w500"

        fun bind(movie: Movies) {
            binding.tvTitleFavorite.text = movie.title
            val fullImage = baseImageUrl + movie.posterPath
            Glide.with(itemView)
                .load(fullImage)
                .into(binding.ivFavorite)
            binding.tvSummary.text = movie.overview

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetailActivity::class.java).apply {
                    putExtra(DetailActivity.ID_MOVIES, movie.id)
                }
                itemView.context.startActivity(intent)
            }
        }
    }
}

