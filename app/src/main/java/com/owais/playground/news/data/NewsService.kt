package com.owais.playground.news.data

import com.owais.playground.Constants
import com.owais.playground.news.model.NewsFeed
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {

    //    country: country code
    //    apiKey: api key
    @GET("top-headlines")
    fun getNewsFeed(
        @Query("country") country: String,
        @Query("apiKey") apiKey: String
    ): Single<NewsFeed>

    companion object {

        fun getService(): NewsService {

            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            val httpClient = OkHttpClient.Builder()
            httpClient.addInterceptor(logging)

            val retrofit = Retrofit.Builder()
                .baseUrl(Constants.NEWS_BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build()
            return retrofit.create(NewsService::class.java)
        }
    }
}