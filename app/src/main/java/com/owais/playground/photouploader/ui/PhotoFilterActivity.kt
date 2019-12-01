package com.owais.playground.photouploader.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.owais.playground.R

class PhotoFilterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.photo_activity)
        setActionBar()

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, PhotoFilterFragment.newInstance())
                .commit()
        }
    }

    private fun setActionBar() {
        supportActionBar?.let {
            title = getString(R.string.photo_features_title)
            it.setDisplayHomeAsUpEnabled(true)
        }
    }
}