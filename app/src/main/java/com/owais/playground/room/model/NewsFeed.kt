package com.owais.playground.room.model

data class NewsFeed (

	val status : String,
	val totalResults : Int,
	val articles : List<Article>
)