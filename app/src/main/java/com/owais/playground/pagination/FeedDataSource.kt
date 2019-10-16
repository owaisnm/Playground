package com.owais.playground.pagination

import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.owais.playground.Constants
import com.owais.playground.pagination.model.Image
import com.owais.playground.pagination.model.ImageFeed
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.CompositeDisposable

class FeedDataSource(
    private val query: String,
    private val compositeDisposable: CompositeDisposable,
    private val imageService: ImageService
) : PageKeyedDataSource<Int, Image>() {

    private val TAG: String = FeedDataSource::class.java.simpleName
    private val collectionIds = ""

    override fun loadInitial(
        @NonNull params: LoadInitialParams<Int>,
        @NonNull callback: LoadInitialCallback<Int, Image>
    ) {
        compositeDisposable.add(
            imageService.getImageFeed(
                Constants.UNSPLASH_ACCESS_KEY,
                query,
                1,
                Constants.PAGE_SIZE,
                collectionIds,
                Orientation.portrait.name
            ).subscribe({ t: ImageFeed? ->
                if (t != null) {
                    callback.onResult(t.images, null, 2)
                } else {
                    Log.d(TAG, "loadInitial object is null")
                }
            }, { t: Throwable? ->
                val errorMessage = if (t == null) Constants.UNKNOWN_ERROR else t.message
                Log.d(TAG, errorMessage)
            })
        )
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

        compositeDisposable.add(
            imageService.getImageFeed(
                Constants.UNSPLASH_ACCESS_KEY,
                query,
                params.key,
                params.requestedLoadSize,
                "",
                Orientation.portrait.name
            ).subscribe({ t: ImageFeed? ->
                val nextKey =
                    if (params.key == t?.total_pages) null else params.key + 1
                val images = t?.images
                if (images != null) {
                    callback.onResult(images, nextKey)
                } else {
                    Log.d(TAG, "loadAfter object is null")
                }
            }, { t: Throwable? ->

                val errorMessage = if (t == null) Constants.UNKNOWN_ERROR else t.message
                Log.i(TAG, errorMessage)
            })
        )
    }
}
