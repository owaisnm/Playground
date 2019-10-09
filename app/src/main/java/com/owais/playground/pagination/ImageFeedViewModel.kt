package com.owais.playground.pagination

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.owais.playground.Constants
import com.owais.playground.pagination.model.Image
import java.util.concurrent.Executors


class ImageFeedViewModel : ViewModel() {

    private var executor = Executors.newFixedThreadPool(5)
    var imageLiveData: LiveData<PagedList<Image>>

    init {
        var feedDataFactory = FeedDataFactory()

        val pagedListConfig = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(Constants.INITIAL_LOAD_SIZE)
            .setPageSize(Constants.PAGE_SIZE).build()

        imageLiveData = LivePagedListBuilder(feedDataFactory, pagedListConfig)
            .setFetchExecutor(executor)
            .build()
    }
}