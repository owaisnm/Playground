package com.owais.playground.news.data

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.owais.playground.news.viewmodel.NewsFeedViewModel

class NewsFeedViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NewsFeedViewModel(application) as T
    }
}