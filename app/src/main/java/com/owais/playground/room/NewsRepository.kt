package com.owais.playground.room

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import com.owais.playground.Constants
import com.owais.playground.SharedPreference
import com.owais.playground.SharedPreference.Companion.REFRESH_TIME_MIN
import com.owais.playground.room.model.Article
import com.owais.playground.room.model.ArticleDao
import com.owais.playground.room.model.NewsFeed
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.*
import java.util.concurrent.Executors

class NewsRepository(
    application: Application,
    private val compositeDisposable: CompositeDisposable,
    private val articleDao: ArticleDao
) {

    val executorService = Executors.newFixedThreadPool(5)
    var articlesLiveData: LiveData<List<Article>> = articleDao.getAll()
    private val newsService = NewsService.getService()
    private val sharedPreference = SharedPreference(application)

    companion object {
        private val TAG: String = NewsFeedViewModel::class.java.simpleName
    }

    init {
        initialLoad()
    }

    private fun initialLoad() {

        if (sharedPreference.getValueLong(REFRESH_TIME_MIN) == 0L) {
            getArticles(true)
        }
    }

    fun getArticles(force: Boolean) {
        var hasMaxRefreshTimePassed = sharedPreference.getValueLong(REFRESH_TIME_MIN) < getMaxRefreshTime(
                Date()
            ).time
        if (force || hasMaxRefreshTimePassed) {
            compositeDisposable.add(
                newsService.getNewsFeed(
                    Constants.COUNTRY_CODE,
                    Constants.NEWS_KEY
                ).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ t: NewsFeed? ->
                        if (t != null) {
                            executorService.execute {
                                articleDao.insertAll(t.articles)
                            }
                        }
                    }, { t: Throwable? ->
                        Log.d(TAG, "getNewsFeed error ", t)
                    })
            )
        }
    }

    private fun getMaxRefreshTime(currentDate: Date): Date {
        var calendar = Calendar.getInstance()
        calendar.time = currentDate
        calendar.add(Calendar.MINUTE, -Constants.FRESH_TIMEOUT_MIN)
        return Calendar.getInstance().time
    }

}
