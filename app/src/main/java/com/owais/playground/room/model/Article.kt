package com.owais.playground.room.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "articles")
data class Article(
    @Embedded(prefix = "source_")
    val source: Source?,
    val author: String?,
    @PrimaryKey(autoGenerate = false)
    val title: String,
    val description: String?,
    val url: String?,
    @ColumnInfo(name = "url_to_image") val urlToImage: String?,
    @ColumnInfo(name = "published_at") val publishedAt: String,
    val content: String?
) {

    override fun equals(other: Any?): Boolean {

        if (this == other) return true
        if (other?.javaClass != javaClass) return false

        other as Article
        return title.equals(other.title) && publishedAt.equals(other.publishedAt)
    }

    override fun hashCode(): Int {
        return title.hashCode() * publishedAt.hashCode()
    }
}