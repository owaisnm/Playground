package com.owais.playground.images.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.jakewharton.rxbinding3.widget.TextViewAfterTextChangeEvent
import com.jakewharton.rxbinding3.widget.afterTextChangeEvents
import com.owais.playground.Constants
import com.owais.playground.R
import com.owais.playground.databinding.FeedActivityBinding
import com.owais.playground.images.model.Image
import com.owais.playground.images.viewmodel.ImageFeedViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class ImagesActivity : AppCompatActivity() {

    private lateinit var adapter: ImageFeedListAdapter
    private lateinit var viewModel: ImageFeedViewModel
    private lateinit var binding: FeedActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.images_activity)
        supportActionBar?.let {
            title = getString(R.string.images_feature_title)
            it.setDisplayHomeAsUpEnabled(true)
        }

        binding = DataBindingUtil.setContentView(this, R.layout.images_activity)
        viewModel = ViewModelProviders.of(this).get(ImageFeedViewModel::class.java)

        initAdapter()
        initSearch()
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

    private fun initAdapter() {
        binding.recyclerview.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapter = ImageFeedListAdapter(this)
        viewModel.imagesLiveData
            .observe(
                this,
                Observer<PagedList<Image>> { pagedList -> adapter.submitList(pagedList) })
        binding.recyclerview.adapter = adapter
    }

    private fun initSearch() {

        binding.searchEdittext.afterTextChangeEvents()
            .map { t: TextViewAfterTextChangeEvent -> t.editable.toString() }
            .debounce(Constants.DEBOUNCE_MS.toLong(), TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { viewModel.loadData(it) }
    }
}