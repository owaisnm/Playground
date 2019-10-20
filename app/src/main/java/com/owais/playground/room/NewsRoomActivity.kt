package com.owais.playground.room

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.owais.playground.R
import com.owais.playground.databinding.RoomActivityBinding
import com.owais.playground.room.model.Article

class NewsRoomActivity : AppCompatActivity() {

    private lateinit var adapter: NewsFeedListAdapter
    private lateinit var viewModel: NewsFeedViewModel
    private lateinit var binding: RoomActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.news_room_activity)

        supportActionBar.let {
            title = getString(R.string.room)
        }

        binding = DataBindingUtil.setContentView(this, R.layout.news_room_activity)

        initViewModel()
        initRecyclerView()
        initSwipeView()
    }

    private fun initViewModel() {
        val factory = NewsFeedViewModelFactory(this.application)
        viewModel =
            ViewModelProviders.of(this, factory).get(NewsFeedViewModel::class.java!!)
        adapter = NewsFeedListAdapter()
        var observer = Observer<List<Article>> { list ->
            binding.swipeRefreshLayout.isRefreshing = false
            adapter.setArticles(list)
        }
        viewModel.articlesLiveData.observe(this, observer)
    }

    private fun initRecyclerView() {
        binding.recyclerview.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        var divider = DividerItemDecoration(this, LinearLayoutManager.VERTICAL)
        binding.recyclerview.addItemDecoration(divider)
        binding.recyclerview.adapter = adapter
    }

    private fun initSwipeView() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.getArticles(true)
        }
    }
}
