package com.owais.playground.pagination

import androidx.recyclerview.widget.DiffUtil
import com.owais.playground.pagination.model.Image

class ImageDiffCallback : DiffUtil.ItemCallback<Image>() {
    override fun areItemsTheSame(oldItem: Image, newItem: Image): Boolean {
        return oldItem.id == newItem.id

    }
    override fun areContentsTheSame(oldItem: Image, newItem: Image): Boolean {
        return oldItem.equals(newItem)
    }
}