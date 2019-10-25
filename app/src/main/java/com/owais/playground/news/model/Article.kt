package com.owais.playground.news.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.owais.playground.Utils

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
    val publishedAt: String?,
    @ColumnInfo(name = "published_at_milli")
    val publishedAtMilli: Long? = (if (publishedAt != null) {
        Utils.dateToMillis(
            publishedAt,
            "yyy-MM-ddTHH:mm:ssz"
        )
    } else {
        null
    }),
    val content: String?
) {

    override fun equals(other: Any?): Boolean {

        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        other as Article
        return title.equals(other.title) && publishedAt.equals(other.publishedAt)
    }

    override fun hashCode(): Int {
        return title.hashCode() * publishedAt.hashCode()
    }
}