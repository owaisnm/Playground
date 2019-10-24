package com.owais.playground.news.model

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface ArticleDao {

    @Query("SELECT * FROM articles ORDER BY CASE WHEN published_at_milli IS NULL THEN 1 ELSE 0 END, published_at_milli DESC")
    fun getAll(): LiveData<List<Article>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(articles: List<Article>)

    @Delete
    fun delete(article: Article)
}