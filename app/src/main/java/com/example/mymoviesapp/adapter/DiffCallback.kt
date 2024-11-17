package com.example.mymoviesapp.adapter

import androidx.recyclerview.widget.DiffUtil

fun <T> createDiffCallback(
    areItemsTheSame: (oldItem: T, newItem: T) -> Boolean,
    areContentsTheSame: (oldItem: T, newItem: T) -> Boolean
): DiffUtil.ItemCallback<T> {
    return object : DiffUtil.ItemCallback<T>() {
        override fun areItemsTheSame(oldItem: T & Any, newItem: T & Any): Boolean {
            return areItemsTheSame(oldItem,newItem)
        }

        override fun areContentsTheSame(oldItem: T & Any, newItem: T & Any): Boolean {
            return areContentsTheSame(oldItem,newItem)
        }

    }
}