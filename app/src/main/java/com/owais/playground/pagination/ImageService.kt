package com.owais.playground.pagination

import com.owais.playground.Constants
import com.owais.playground.pagination.model.ImageFeed
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ImageService {


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
    ): Single<ImageFeed>

    companion object {

        fun getService(): ImageService {

            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            val httpClient = OkHttpClient.Builder()
            httpClient.addInterceptor(logging)

            val retrofit = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build()
            return retrofit.create(ImageService::class.java)
        }
    }
}