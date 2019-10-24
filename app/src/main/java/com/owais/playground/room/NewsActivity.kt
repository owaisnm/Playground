package com.owais.playground.room

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.owais.playground.R
import com.owais.playground.databinding.NewsActivityBinding

class NewsActivity : AppCompatActivity() {

    private lateinit var adapter: NewsFeedListAdapter
    private lateinit var binding: NewsActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.news_activity)

        supportActionBar.let {
            title = getString(R.string.room)
        }

        initViewModel()
        initRecyclerView()
    }

    private fun initViewModel() {
        val factory = NewsFeedViewModelFactory(this.application)
        binding = DataBindingUtil.setContentView(this, R.layout.news_activity)
        binding.lifecycleOwner = this
        binding.viewModel=
            ViewModelProviders.of(this, factory).get(NewsFeedViewModel::class.java)
        adapter = NewsFeedListAdapter(binding.viewModel!!)
    }

    private fun initRecyclerView() {
        binding.recyclerview.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        var divider = DividerItemDecoration(this, LinearLayoutManager.VERTICAL)
        binding.recyclerview.addItemDecoration(divider)
        binding.recyclerview.adapter = adapter
    }
}
