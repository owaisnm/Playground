package com.owais.playground.images.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.owais.playground.R

class ImagesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.images_activity)
        supportActionBar?.let {
            title = getString(R.string.images_feature_title)
            it.setDisplayHomeAsUpEnabled(true)
        }

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.images_fragment_container, ImagesFragment.newInstance())
                .addToBackStack(null).commit()
        }

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        item?.let {
            when (item.getItemId()) {
                android.R.id.home -> {
                    this.finish()
                    return true
                }
                else -> return super.onOptionsItemSelected(item)
            }
        }
        return super.onOptionsItemSelected(item)
    }

}