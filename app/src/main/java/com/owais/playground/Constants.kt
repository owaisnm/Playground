package com.owais.playground

class Constants {

    companion object {

        // images (pagination)
        const val UNSPLASH_BASE_URL = "https://api.unsplash.com/"
        const val NEWS_BASE_URL = "https://newsapi.org/v2/"
        const val UNKNOWN_ERROR = "Unknown Error"
        const val INITIAL_LOAD_SIZE_HINT: Int = 10
        const val PAGE_SIZE: Int = 10
        const val IMAGE_SIZE: Int = 250
        const val UNSPLASH_ACCESS_KEY: String = BuildConfig.UnsplashAccessKey

        //news (room)
        const val NEWS_KEY: String = BuildConfig.NewsKey
        const val DEBOUNCE_MS = 800
        const val COUNTRY_CODE = "us"
        const val NEWS_ARTICLE_DB_NAME = "Articles.db"
        const val FRESH_TIMEOUT_MIN = 1
        const val PLAYGROUND_PREFERENCES = "playground_preferences"
        const val ITEMS = "items"

        //photo (work manager)
        @JvmField
        val VERBOSE_NOTIFICATION_CHANNEL_NAME: CharSequence =
            "Verbose WorkManager Notifications"
        const val VERBOSE_NOTIFICATION_CHANNEL_DESCRIPTION =
            "Shows notifications whenever work starts"
        @JvmField
        val NOTIFICATION_TITLE: CharSequence = "WorkRequest Starting"
        const val CHANNEL_ID = "VERBOSE_NOTIFICATION"
        const val NOTIFICATION_ID = 1

        // The name of the image manipulation work
        const val IMAGE_MANIPULATION_WORK_NAME = "image_manipulation_work"

        // Other keys
        const val OUTPUT_PATH = "sepia_filter_outputs"
        const val KEY_IMAGE_URI = "KEY_IMAGE_URI"
        const val TAG_OUTPUT = "OUTPUT"

        const val DELAY_TIME_MILLIS: Long = 3000

    }
}