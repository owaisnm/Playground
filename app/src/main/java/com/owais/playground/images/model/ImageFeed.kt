package com.owais.playground.images.model

import com.google.gson.annotations.SerializedName

data class ImageFeed (

    val total: Int,
    val total_pages: Int,
    @SerializedName("results")
    val images: List<Image>
)