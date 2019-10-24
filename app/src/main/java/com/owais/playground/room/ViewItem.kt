package com.owais.playground.room

import com.owais.playground.R

sealed class ViewItem(val resource: Int) {
    class ArticleView(val name: String) : ViewItem(R.layout.article_item)
}