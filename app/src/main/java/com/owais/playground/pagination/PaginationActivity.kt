package com.owais.playground.pagination

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.owais.playground.R
import com.owais.playground.databinding.FeedActivityBinding
import com.owais.playground.pagination.model.Image


class PaginationActivity : AppCompatActivity() {

    private lateinit var adapter: FeedListAdapter
    private lateinit var viewModel: ImageFeedViewModel
    private lateinit var binding: FeedActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pagination_activity)
        supportActionBar.let {
            title = getString(R.string.pagination)
        }

        binding = DataBindingUtil.setContentView(this, R.layout.pagination_activity)
        viewModel = ViewModelProviders.of(this).get(ImageFeedViewModel::class.java!!)
        binding.recyclerview.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapter = FeedListAdapter(this)
        viewModel.imageLiveData
            .observe(
                this,
                Observer<PagedList<Image>> { pagedList -> adapter.submitList(pagedList) })
        binding.recyclerview.adapter = adapter

    }
}