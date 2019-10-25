package com.owais.playground.images.data

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.owais.playground.Constants
import com.owais.playground.images.model.Image
import io.reactivex.disposables.CompositeDisposable
import java.util.concurrent.Executors

class ImageRepository(
    compositeDisposable: CompositeDisposable
) {

    var imagesLiveData: LiveData<PagedList<Image>>
    private val feedDataFactory = FeedDataFactory(
        compositeDisposable,
        ImageService.getService()
    )

    private var executor = Executors.newFixedThreadPool(5)

    init {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(Constants.INITIAL_LOAD_SIZE_HINT)
            .setPageSize(Constants.PAGE_SIZE).build()

        imagesLiveData = LivePagedListBuilder(feedDataFactory, config)
            .setFetchExecutor(executor)
            .build()
    }

    fun loadData(query: String) {
        feedDataFactory.invalidateDataSource(query)
    }

}