package com.owais.playground.room

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.owais.playground.databinding.ArticleItemBinding
import com.owais.playground.room.model.Article

class NewsFeedListAdapter :
    RecyclerView.Adapter<NewsFeedListAdapter.ArticleItemViewHolder>() {

    private var articles = emptyList<Article>()

    fun setArticles(articles: List<Article>) {
        this.articles = articles
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding = ArticleItemBinding.inflate(layoutInflater, parent, false)
        return ArticleItemViewHolder(itemBinding)
    }

    override fun getItemCount(): Int {
        return articles.size
    }

    override fun onBindViewHolder(holder: ArticleItemViewHolder, position: Int) {
        holder.bindTo(articles[position])
    }

    inner class ArticleItemViewHolder(
        private val binding: ArticleItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bindTo(article: Article?) {
            binding.articleDescriptionTextview.visibility = View.GONE
            if (article != null) {
                binding.articleTitleTextview.text = article.title
                article.description?.let {
                    binding.articleDescriptionTextview.text = article.description
                    binding.articleDescriptionTextview.visibility = View.VISIBLE
                }
            }
        }
    }
}