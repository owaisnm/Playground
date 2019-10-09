package com.owais.playground.pagination

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.owais.playground.pagination.model.Image

class FeedDataFactory : DataSource.Factory<Int, Image>() {

    val mutableLiveData = MutableLiveData<FeedDataSource>()
    private lateinit var feedDataSource: FeedDataSource

    override fun create(): DataSource<Int, Image> {
        feedDataSource = FeedDataSource()
        mutableLiveData.postValue(feedDataSource)
        return feedDataSource
    }

}