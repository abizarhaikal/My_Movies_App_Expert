package com.example.mymoviesapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.core.data.CastItem
import com.example.core.domain.model.CastItemModel
import com.example.mymoviesapp.R
import com.example.mymoviesapp.databinding.ItemActorsBinding

class CastSeriesAdapter : ListAdapter<CastItemModel, CastSeriesAdapter.CastSeriesViewHolder>(
    createDiffCallback(
        areItemsTheSame = { oldItem, newItem -> oldItem.id == newItem.id },
        areContentsTheSame = { oldItem, newItem -> oldItem.id == newItem.id }
    )
) {
    private val url = "https://image.tmdb.org/t/p/w500"

    inner class CastSeriesViewHolder(private val binding: ItemActorsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: CastItemModel) {
            binding.tvActors.text = data.name
            val imageUrl = url + data.profilePath
            if (data.profilePath != null) {
                Glide.with(itemView.context)
                    .load(imageUrl)
                    .circleCrop()
                    .into(binding.ivActors)
            } else {
                Glide.with(itemView.context)
                    .load(R.drawable.empty_image)
                    .circleCrop()
                    .into(binding.ivActors)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CastSeriesAdapter.CastSeriesViewHolder {
        val binding = ItemActorsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CastSeriesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CastSeriesAdapter.CastSeriesViewHolder, position: Int) {
        val data = getItem(position)
        holder.bind(data)
    }

}