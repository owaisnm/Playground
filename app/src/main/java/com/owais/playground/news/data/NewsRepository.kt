package com.owais.playground.news.data

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import com.owais.playground.Constants
import com.owais.playground.SharedPreference
import com.owais.playground.SharedPreference.Companion.REFRESH_TIME_MIN
import com.owais.playground.news.model.Article
import com.owais.playground.news.model.ArticleDao
import com.owais.playground.news.model.NewsFeed
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.*

class NewsRepository(
    application: Application,
    private val compositeDisposable: CompositeDisposable,
    private val articleDao: ArticleDao
) {

    var articlesLiveData: LiveData<List<Article>> = articleDao.getAll()
    private val newsService = NewsService.getService()
    private val sharedPreference = SharedPreference(application)

    companion object {
        private val TAG: String = NewsRepository::class.java.simpleName
    }

    init {
        loadData()
    }

    private fun loadData() {
        var dataExists = sharedPreference.getValueLong(REFRESH_TIME_MIN) != 0L
        var hasMaxRefreshTimePassed =
            sharedPreference.getValueLong(REFRESH_TIME_MIN) < getMaxRefreshTime(
                Date()
            ).time
        if (!dataExists || hasMaxRefreshTimePassed) {
            getArticles(null)
        }
    }

    fun getArticles(callback: GetArticlesCallback?) {
        compositeDisposable.add(
            newsService.getNewsFeed(
                Constants.COUNTRY_CODE,
                Constants.NEWS_KEY
            ).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe({ t: NewsFeed? ->
                    Log.d(TAG, "getNewsFeed success")
                    if (t != null) {
                        insertAll(t.articles)
                    }
                    callback?.let { callback.onSuccess(t?.articles) }
                }, { t: Throwable? ->
                    Log.d(TAG, "getNewsFeed error ", t)
                    callback?.let { callback.onFailure(t) }
                })
        )
    }

    private fun insertAll(items: List<Article>) {
        Completable.fromAction {
            articleDao.insertAll(items)
        }.observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object : CompletableObserver {
                override fun onComplete() {
                    Log.d(TAG, "insertAll success")
                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onError(e: Throwable) {
                    Log.d(TAG, "insertAll error", e)
                }
            })
    }


    fun delete(item: Article) {
        Completable.fromAction {
            articleDao.delete(item)
        }.observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object : CompletableObserver {
                override fun onComplete() {
                    Log.d(TAG, "delete success")
                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onError(e: Throwable) {
                    Log.d(TAG, "delete error", e)
                }
            })
    }

    private fun getMaxRefreshTime(currentDate: Date): Date {
        var calendar = Calendar.getInstance()
        calendar.time = currentDate
        calendar.add(Calendar.MINUTE, -Constants.FRESH_TIMEOUT_MIN)
        return Calendar.getInstance().time
    }

    interface GetArticlesCallback {
        fun onSuccess(articles: List<Article>?)
        fun onFailure(t: Throwable?)
    }
}
