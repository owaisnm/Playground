package com.owais.playground.news.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.owais.playground.Constants
import com.owais.playground.R
import com.owais.playground.databinding.ArticleItemBinding
import com.owais.playground.news.model.Article
import com.owais.playground.news.viewmodel.NewsFeedViewModel

class NewsFeedListAdapter(feedViewModel: NewsFeedViewModel) :
    RecyclerView.Adapter<NewsFeedListAdapter.ArticleItemViewHolder>() {

    private val feedViewModel: NewsFeedViewModel = feedViewModel
    private var items: List<Article> = emptyList()

    companion object {
        @JvmStatic
        @BindingAdapter(Constants.ITEMS)
        fun RecyclerView.bindItems(items: List<Article>?) {
            val adapter = adapter as NewsFeedListAdapter
            items?.let {
                adapter.setItems(items)
            }
        }
    }

    fun setItems(items: List<Article>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding = ArticleItemBinding.inflate(layoutInflater, parent, false)
        return ArticleItemViewHolder(
            parent,
            itemBinding
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ArticleItemViewHolder, position: Int) {
        holder.bind(items[position], feedViewModel)
    }

    abstract class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    class ArticleItemViewHolder(
        private val parent: ViewGroup,
        private val binding: ArticleItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.article_item,
            parent,
            false
        )
    ) : ViewHolder(binding.root) {
        fun bind(item: Article, viewmodel: NewsFeedViewModel) {
            binding.item = item
            binding.viewModel = viewmodel
        }
    }
}