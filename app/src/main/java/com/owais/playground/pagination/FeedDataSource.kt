package com.owais.playground.pagination

import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.owais.playground.Constants
import com.owais.playground.pagination.model.Image
import com.owais.playground.pagination.model.ImageFeed
import io.reactivex.annotations.NonNull
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class FeedDataSource : PageKeyedDataSource<Int, Image>() {
    private val TAG: String = FeedDataSource::class.java.simpleName
    private val BASE_URL = "https://api.unsplash.com"
    private val imageApi: ImageApi
    //todo replace with edit text
    val query = "apple"
    val collectionIds = ""

    init {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(logging)

        val retrofit = Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(httpClient.build())
            .build()
        imageApi = retrofit.create(ImageApi::class.java)
    }

    override fun loadInitial(
        @NonNull params: LoadInitialParams<Int>,
        @NonNull callback: LoadInitialCallback<Int, Image>
    ) {

        imageApi.getImageFeed(
            Constants.UNSPLASH_ACCESS_KEY,
            query,
            1,
            Constants.PAGE_SIZE,
            collectionIds,
            Orientation.portrait.name
        )
            .enqueue(object : Callback<ImageFeed> {
                override fun onResponse(call: Call<ImageFeed>, response: Response<ImageFeed>) {
                    if (response.isSuccessful) {
                        var images = response.body()?.images
                        if (images != null) {
                            callback.onResult(images, null, 2)
                        }
                    }
                }

                override fun onFailure(call: Call<ImageFeed>, t: Throwable) {
                    val errorMessage = if (t == null) Constants.UNKNOWN_ERROR else t.message
                    Log.i(TAG, errorMessage)
                }
            })
    }


    override fun loadBefore(
        @NonNull params: LoadParams<Int>,
        @NonNull callback: LoadCallback<Int, Image>
    ) {
    }

    override fun loadAfter(
        @NonNull params: LoadParams<Int>,
        @NonNull callback: LoadCallback<Int, Image>
    ) {

        Log.i(TAG, "Loading Range " + params.key + " Count " + params.requestedLoadSize)

        imageApi.getImageFeed(
            Constants.UNSPLASH_ACCESS_KEY,
            query,
            params.key,
            params.requestedLoadSize,
            "",
            Orientation.portrait.name
        )
            .enqueue(object : Callback<ImageFeed> {
                override fun onResponse(call: Call<ImageFeed>, response: Response<ImageFeed>) {
                    if (response.isSuccessful) {
                        val nextKey =
                            if (params.key == response?.body()?.total_pages) null else params.key + 1
                        val images = response.body()?.images
                        if (images != null) {
                            callback.onResult(images, nextKey)
                        }
                    }
                }

                override fun onFailure(call: Call<ImageFeed>, t: Throwable?) {
                    val errorMessage = if (t == null) Constants.UNKNOWN_ERROR else t.message
                    Log.i(TAG, errorMessage)
                }
            })
    }
}
