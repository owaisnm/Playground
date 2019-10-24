package com.owais.playground

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.owais.playground.databinding.MainActivityBinding
import com.owais.playground.images.ui.ImagesActivity
import com.owais.playground.news.ui.NewsActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var binding: MainActivityBinding =
                DataBindingUtil.setContentView(this, R.layout.main_activity)
        binding.presenter = this

    }

    fun openImagesPaginationActivity() {
        startActivity(Intent(this, ImagesActivity::class.java))
    }

    fun openNewsRoomActivity() {
        startActivity(Intent(this, NewsActivity::class.java))
    }
}