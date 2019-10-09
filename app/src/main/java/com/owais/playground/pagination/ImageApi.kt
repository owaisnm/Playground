package com.owais.playground.pagination

import com.owais.playground.pagination.model.ImageFeed
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ImageApi {


    //    query	Search terms.
    //    page	Page number to retrieve. (Optional; default: 1)
    //    per_page	Number of items per page. (Optional; default: 10)
    //    collections	Collection ID(‘s) to narrow search. If multiple, comma-separated.
    //    orientation	Filter search results by photo orientation. Valid values are landscape, portrait, and squarish.
    @GET("/search/photos")
    fun getImageFeed(
        @Query("client_id") apiKey: String,
        @Query("query") query: String, @Query("page") page: Int, @Query("per_page") perPage: Int, @Query(
            "collections"
        ) collections: String, @Query("orientation") orientation: String
    ): Call<ImageFeed>
}