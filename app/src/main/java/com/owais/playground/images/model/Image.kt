package com.owais.playground.images.model

import com.google.gson.annotations.SerializedName

data class Image(
    val id: String,
    val created_at: String,
    val width: Int,
    val height: Int,
    val color: String,
    val likes: Int,
    val liked_by_user: Boolean,
    val description: String,
    val user: User,
    @SerializedName("current_user_collections")
    val currentUserCollections: List<CurrentUserCollection>,
    val urls: Urls,
    val links: Links
) {

    override fun equals(other: Any?): Boolean {
        if (this == other) return true
        if (other?.javaClass != javaClass) return false

        return id.equals((other as Image).id)
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}