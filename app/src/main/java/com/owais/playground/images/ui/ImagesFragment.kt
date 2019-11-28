package com.owais.playground.images.ui

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
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

class ImagesFragment : Fragment() {


    private lateinit var adapter: ImageFeedListAdapter
    private lateinit var viewModel: ImageFeedViewModel
    private lateinit var binding: FeedActivityBinding

    companion object {
        fun newInstance(): ImagesFragment {
            return ImagesFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(
            R.layout.images_fragment, container,
            false
        )
        binding = DataBindingUtil.setContentView(activity as Activity, R.layout.images_fragment)
        viewModel = ViewModelProviders.of(this).get(ImageFeedViewModel::class.java)

        initAdapter()
        initSearch()
        return view
    }

    private fun initAdapter() {
        val activity = activity as Context
        binding.recyclerview.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        adapter = ImageFeedListAdapter(activity)
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