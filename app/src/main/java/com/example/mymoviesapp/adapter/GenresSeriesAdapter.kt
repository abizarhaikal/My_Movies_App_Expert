package com.example.mymoviesapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.core.data.GenresItemSeries
import com.example.core.domain.model.Genre
import com.example.mymoviesapp.databinding.ItemsGenreBinding

class GenresSeriesAdapter :
    ListAdapter<Genre, GenresSeriesAdapter.DetailSeriesViewHolder>(
        createDiffCallback(
            areItemsTheSame = { oldItem, newItem -> oldItem.id == newItem.id },
            areContentsTheSame = { oldItem, newItem -> oldItem.id == newItem.id }
        )
    ) {
    inner class DetailSeriesViewHolder(private val binding: ItemsGenreBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Genre) {
            binding.tvGenres.text = data.name
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GenresSeriesAdapter.DetailSeriesViewHolder {
        val binding = ItemsGenreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DetailSeriesViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: GenresSeriesAdapter.DetailSeriesViewHolder,
        position: Int
    ) {
        val data = getItem(position)
        holder.bind(data)
    }

}