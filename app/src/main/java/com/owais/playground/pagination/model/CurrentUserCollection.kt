package com.owais.playground.pagination.model

data class CurrentUserCollection(

    val id: Int,
    val title: String,
    val published_at: String,
    val updated_at: String,
    val curated: Boolean,
    val cover_photo: String,
    val user: String
)