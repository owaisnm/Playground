package com.owais.playground.news.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.owais.playground.news.data.NewsRepository
import com.owais.playground.news.model.Article
import com.owais.playground.news.model.ArticleDatabase
import io.reactivex.disposables.CompositeDisposable

class NewsFeedViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: NewsRepository
    private val compositeDisposable = CompositeDisposable()
    private val articleDao = ArticleDatabase.getAppDataBase(application).newsDao()
    var articlesLiveData: LiveData<List<Article>>

    private val _isLoading = MutableLiveData<Boolean>().apply { value = false }
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        repository = NewsRepository(
            getApplication(),
            compositeDisposable,
            articleDao
        )
        articlesLiveData = repository.articlesLiveData
    }

    fun onRefresh() {
        _isLoading.value = true
        getArticles()
    }

    private fun getArticles() {
        repository.getArticles(object :
            NewsRepository.GetArticlesCallback {
            override fun onSuccess(articles: List<Article>?) {
                _isLoading.value = false
            }

            override fun onFailure(t: Throwable?) {
                _isLoading.value = false
            }
        })
    }

    fun delete(article: Article) {
        repository.delete(article)
    }

    override fun onCleared() {
        compositeDisposable.clear()
    }
}
