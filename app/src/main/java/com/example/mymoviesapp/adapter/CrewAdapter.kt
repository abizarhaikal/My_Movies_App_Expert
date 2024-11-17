package com.example.mymoviesapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.core.domain.model.CastItemModel
import com.example.mymoviesapp.R
import com.example.mymoviesapp.databinding.ItemActorsBinding

class CrewAdapter : ListAdapter<CastItemModel, CrewAdapter.ViewHolder>(createDiffCallback(
    areItemsTheSame = { old, new -> old.id == new.id },
    areContentsTheSame = { old, new -> old.id == new.id }
)) {
    private val url = "https://image.tmdb.org/t/p/w500"

    inner class ViewHolder(private val binding: ItemActorsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CastItemModel) {
            val fullUrl = url + item.profilePath
            if (item.profilePath == null) {
                Glide.with(itemView.context)
                    .load(R.drawable.empty_image)
                    .circleCrop()
                    .into(binding.ivActors)
            } else {
                Glide.with(itemView.context)
                    .load(fullUrl)
                    .circleCrop()
                    .into(binding.ivActors)
            }
            binding.tvActors.text = item.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CrewAdapter.ViewHolder {
        val binding = ItemActorsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CrewAdapter.ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }
}