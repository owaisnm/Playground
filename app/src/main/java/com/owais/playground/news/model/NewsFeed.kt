package com.owais.playground.news.model

data class NewsFeed (

	val status : String,
	val totalResults : Int,
	val articles : List<Article>
)