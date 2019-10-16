package com.owais.playground.pagination

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.owais.playground.Constants
import com.owais.playground.pagination.model.Image
import io.reactivex.disposables.CompositeDisposable
import java.util.concurrent.Executors


class ImageFeedViewModel : ViewModel() {

    var imagesLiveData: LiveData<PagedList<Image>>
    private val compositeDisposable = CompositeDisposable()
    private val feedDataFactory: FeedDataFactory

    private var executor = Executors.newFixedThreadPool(5)

    init {
        feedDataFactory = FeedDataFactory(compositeDisposable, ImageService.getService())

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(Constants.INITIAL_LOAD_SIZE_HINT)
            .setPageSize(Constants.PAGE_SIZE).build()

        imagesLiveData = LivePagedListBuilder(feedDataFactory, config)
            .setFetchExecutor(executor)
            .build()
    }

    fun invalidate(query: String) {
        feedDataFactory.invalidateDataSource(query)
    }

    override fun onCleared() {
        compositeDisposable.clear()
    }
}