package com.owais.playground.room.model

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface ArticleDao {

    @Query("SELECT * FROM articles")
    fun getAll(): LiveData<List<Article>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(articles: List<Article>)

    @Delete
    fun delete(article: Article)
}