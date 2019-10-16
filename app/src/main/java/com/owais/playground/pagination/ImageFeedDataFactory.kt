package com.owais.playground.pagination

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.owais.playground.pagination.model.Image
import io.reactivex.disposables.CompositeDisposable

class FeedDataFactory(
    private val composeDisposable: CompositeDisposable,
    private val imageService: ImageService
) : DataSource.Factory<Int, Image>() {

    var queryString = ""

    private val feedDataSourceLiveData = MutableLiveData<FeedDataSource>()

    override fun create(): DataSource<Int, Image> {
        val feedDataSource = FeedDataSource(queryString, composeDisposable, imageService)
        feedDataSourceLiveData.postValue(feedDataSource)
        return feedDataSource
    }

    fun invalidateDataSource(query: String) {
        queryString = query
        feedDataSourceLiveData.value?.invalidate()
    }
}