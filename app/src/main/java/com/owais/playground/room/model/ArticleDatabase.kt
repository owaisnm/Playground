package com.owais.playground.room.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.owais.playground.Constants
import com.owais.playground.Convertor

@Database(entities = arrayOf(Article::class), version = 1)
@TypeConverters(Convertor::class)
abstract class ArticleDatabase : RoomDatabase() {
    abstract fun newsDao(): ArticleDao

    companion object {
        @Volatile
        var INSTANCE: ArticleDatabase? = null

        fun getAppDataBase(context: Context): ArticleDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ArticleDatabase::class.java,
                    Constants.NEWS_ARTICLE_DB_NAME
                ).build()
                INSTANCE = instance
                return instance
            }

        }
    }
}