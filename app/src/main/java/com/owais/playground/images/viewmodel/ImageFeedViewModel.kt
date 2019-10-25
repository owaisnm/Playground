package com.owais.playground.images.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.owais.playground.images.data.ImageRepository
import com.owais.playground.images.model.Image
import io.reactivex.disposables.CompositeDisposable

class ImageFeedViewModel : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    private val imageRepository = ImageRepository(compositeDisposable)
    var imagesLiveData: LiveData<PagedList<Image>> = imageRepository.imagesLiveData

    fun loadData(query: String) {
        imageRepository.loadData(query)
    }

    override fun onCleared() {
        compositeDisposable.clear()
    }
}