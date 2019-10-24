package com.owais.playground

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.owais.playground.databinding.MainActivityBinding
import com.owais.playground.pagination.ImagesPaginationActivity
import com.owais.playground.room.NewsActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var binding: MainActivityBinding =
                DataBindingUtil.setContentView(this, R.layout.main_activity)
        binding.presenter = this

    }

    fun openImagesPaginationActivity() {
        startActivity(Intent(this, ImagesPaginationActivity::class.java))
    }

    fun openNewsRoomActivity() {
        startActivity(Intent(this, NewsActivity::class.java))
    }
}