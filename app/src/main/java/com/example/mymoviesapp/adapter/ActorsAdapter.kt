package com.example.mymoviesapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.core.domain.model.ActorFavorite
import com.example.mymoviesapp.R
import com.example.mymoviesapp.databinding.ItemActorsBinding


class ActorsAdapter : ListAdapter<ActorFavorite, ActorsAdapter.ViewHolder>(
    createDiffCallback(
        areItemsTheSame = { oldItem, newItem ->
            oldItem.id == newItem.id
        },
        areContentsTheSame = { oldItem, newItem ->
            oldItem.id == newItem.id
        }
    )
) {
    private val baseImageUrl = "https://image.tmdb.org/t/p/w500"
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemActorsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    inner class ViewHolder(private val binding: ItemActorsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ActorFavorite) {
            val fullPosterUrl = baseImageUrl + data.profilePath
            if (data.profilePath == null) {
                Glide.with(itemView.context)
                    .load(R.drawable.empty_image)
                    .circleCrop()
                    .into(binding.ivActors)
            } else {
                Glide.with(itemView.context)
                    .load(fullPosterUrl)
                    .circleCrop()
                    .into(binding.ivActors)
            }
            binding.tvActors.text = data.name
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = getItem(position)
        holder.bind(data)
    }


}