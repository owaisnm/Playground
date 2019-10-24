package com.owais.playground.images.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.owais.playground.Constants
import com.owais.playground.R
import com.owais.playground.databinding.FeedItemBinding
import com.owais.playground.images.model.Image
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class ImageFeedListAdapter(contextParam: Context) :
    PagedListAdapter<Image, RecyclerView.ViewHolder>(ImageDiffCallback) {

    private val context = contextParam

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding = FeedItemBinding.inflate(layoutInflater, parent, false)
        return ImageItemViewHolder(context, itemBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ImageItemViewHolder).bindTo(getItem(position))
    }

    inner class ImageItemViewHolder(
        private val context: Context,
        private val binding: FeedItemBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindTo(image: Image?) {
            binding.imageview.visibility = View.GONE
            if (image != null) {
                binding.textview.visibility = View.GONE
                binding.progressBar.visibility = View.VISIBLE
                binding.textview.text = context.getString(R.string.by_author, image.user.username)
                Picasso.get().load(image.urls.regular)
                    .resize(Constants.IMAGE_SIZE, Constants.IMAGE_SIZE)
                    .into(binding.imageview, object : Callback {
                        override fun onSuccess() {
                            binding.progressBar.visibility = View.GONE
                            binding.imageview.visibility = View.VISIBLE
                            binding.textview.visibility = View.VISIBLE
                        }

                        override fun onError(e: Exception?) {
                            binding.progressBar.visibility = View.GONE
                            binding.textview.visibility = View.VISIBLE
                            binding.textview.text = context.getString(R.string.failed)
                        }
                    })
            } else {
                binding.textview.visibility = View.VISIBLE
                binding.textview.text = context.getString(R.string.failed)
            }
        }
    }

    companion object {
        val ImageDiffCallback = object : DiffUtil.ItemCallback<Image>() {
            override fun areItemsTheSame(oldItem: Image, newItem: Image): Boolean {
                return oldItem.id.equals(newItem.id)

            }

            override fun areContentsTheSame(oldItem: Image, newItem: Image): Boolean {
                return oldItem.equals(newItem)
            }
        }
    }
}