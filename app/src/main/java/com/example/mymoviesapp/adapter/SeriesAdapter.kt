package com.example.mymoviesapp.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.core.domain.model.SeriesNow
import com.example.mymoviesapp.databinding.ItemMoviesTrendingBinding
import com.example.mymoviesapp.ui.DetailSeriesActivity

class SeriesAdapter : ListAdapter<SeriesNow, SeriesAdapter.SeriesViewHolder>(
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
    ): SeriesViewHolder {
        val binding =
            ItemMoviesTrendingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SeriesViewHolder(binding)
    }

    inner class SeriesViewHolder(val binding: ItemMoviesTrendingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SeriesNow, isCenter: Boolean) {
            val fullPosterUrl = baseImageUrl + item.posterPath
            Glide.with(itemView.context)
                .load(fullPosterUrl)
                .into(binding.ivMoviesTrending)

            // Update visibility based on center position
            binding.tvItemTitle.visibility = if (isCenter) View.VISIBLE else View.GONE
            binding.viewLayout.visibility = if (isCenter) View.VISIBLE else View.GONE

            val id = item.id
            binding.viewLayout.setOnClickListener {
                val intent = Intent(itemView.context, DetailSeriesActivity::class.java).apply {
                    putExtra(DetailSeriesActivity.ID_SERIES, id)
                }
                itemView.context.startActivity(intent)
            }
        }
    }

    override fun onBindViewHolder(holder: SeriesViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, position == centerPosition)
    }

    fun updateCenterPosition(newPosition: Int) {
        if (newPosition != centerPosition) {
            previousCenterPosition = centerPosition
            centerPosition = newPosition

            // Only notify the changed items
            previousCenterPosition.takeIf { it >= 0 }?.let { notifyItemChanged(it) }
            notifyItemChanged(centerPosition)
        }
    }
}