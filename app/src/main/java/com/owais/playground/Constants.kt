package com.owais.playground

class Constants {

    companion object {

        // pagination
        const val UNSPLASH_BASE_URL = "https://api.unsplash.com/"
        const val NEWS_BASE_URL = "https://newsapi.org/v2/"
        const val UNKNOWN_ERROR = "Unknown Error"
        const val INITIAL_LOAD_SIZE_HINT: Int = 10
        const val PAGE_SIZE: Int = 10
        const val IMAGE_SIZE: Int = 250
        const val UNSPLASH_ACCESS_KEY: String = BuildConfig.UnsplashAccessKey
        const val NEWS_KEY: String = BuildConfig.NewsKey
        const val DEBOUNCE_MS = 800
        const val COUNTRY_CODE = "us"
        const val NEWS_ARTICLE_DB_NAME = "Articles.db"
        const val FRESH_TIMEOUT_MIN = 1
        const val PLAYGROUND_PREFERENCES = "playground_preferences"
        const val ITEMS = "items"
    }
}