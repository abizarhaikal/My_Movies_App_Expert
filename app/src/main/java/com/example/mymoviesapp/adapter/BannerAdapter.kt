package com.example.mymoviesapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.mymoviesapp.R
import com.bumptech.glide.Glide

class BannerAdapter (private val banners: List<Int>) : RecyclerView.Adapter<BannerAdapter.BannerViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BannerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_banner, parent, false)
        return BannerViewHolder(view)
    }

    class BannerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageBanner : ImageView = itemView.findViewById(R.id.imageBanner)
    }

    override fun onBindViewHolder(holder: BannerViewHolder, position: Int) {
        Glide.with(holder.itemView.context)
            .load(banners[position])
            .into(holder.imageBanner)
//        holder.imageBanner.setImageResource(banners[position])
    }

    override fun getItemCount(): Int {
        return banners.size
    }

}