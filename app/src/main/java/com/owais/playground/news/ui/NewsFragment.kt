package com.owais.playground.news.ui

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.owais.playground.R
import com.owais.playground.databinding.NewsFragmentBinding
import com.owais.playground.news.data.NewsFeedViewModelFactory
import com.owais.playground.news.viewmodel.NewsFeedViewModel

class NewsFragment : Fragment() {

    private lateinit var adapter: NewsFeedListAdapter
    private lateinit var binding: NewsFragmentBinding

    companion object {
        fun newInstance(): NewsFragment {
            return NewsFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.news_fragment, container, false)
        if (activity != null) {
            initViewModel()
            initRecyclerView()
        }
        return view
    }

    private fun initViewModel() {
        val factory = NewsFeedViewModelFactory(activity!!.application)
        binding = DataBindingUtil.setContentView(activity as Activity, R.layout.news_fragment)
        binding.lifecycleOwner = this
        binding.viewModel =
            ViewModelProviders.of(this, factory).get(NewsFeedViewModel::class.java)
        adapter = NewsFeedListAdapter(binding.viewModel!!)
    }

    private fun initRecyclerView() {
        binding.recyclerview.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        var divider = DividerItemDecoration(activity, LinearLayoutManager.VERTICAL)
        binding.recyclerview.addItemDecoration(divider)
        binding.recyclerview.adapter = adapter
    }

}