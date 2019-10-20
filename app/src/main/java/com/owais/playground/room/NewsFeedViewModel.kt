package com.owais.playground.room

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.owais.playground.room.model.Article
import com.owais.playground.room.model.ArticleDatabase
import io.reactivex.disposables.CompositeDisposable

class NewsFeedViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: NewsRepository
    private val compositeDisposable = CompositeDisposable()
    var articlesLiveData: LiveData<List<Article>>
    val articleDao = ArticleDatabase.getAppDataBase(application).newsDao()

    init {
        repository = NewsRepository(getApplication(), compositeDisposable, articleDao)
        articlesLiveData = repository.articlesLiveData
    }

    fun getArticles() {
        repository.getArticles()
    }

    override fun onCleared() {
        compositeDisposable.clear()
    }
}