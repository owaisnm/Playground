package com.owais.playground

class Constants {

    companion object {
        const val BASE_URL = "https://api.unsplash.com"
        const val UNKNOWN_ERROR = "Unknown Error"
        const val INITIAL_LOAD_SIZE_HINT: Int = 10
        const val PAGE_SIZE: Int = 10
        const val IMAGE_SIZE: Int = 250
        const val UNSPLASH_ACCESS_KEY: String = BuildConfig.UnsplashAccessKey
        const val DEBOUNCE_MS = 800
    }
}