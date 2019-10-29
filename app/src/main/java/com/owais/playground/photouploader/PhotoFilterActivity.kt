package com.owais.playground.photouploader

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.owais.playground.R
import com.owais.playground.databinding.PhotoActivityBinding
import com.owais.playground.photouploader.PermissionsManager.Companion.REQUEST_CODE_PERMISSIONS
import com.owais.playground.photouploader.viewmodel.PhotoFilterViewModel

class PhotoFilterActivity : AppCompatActivity() {

    private lateinit var filterViewModel: PhotoFilterViewModel
    private lateinit var binding: PhotoActivityBinding

    private val TAG by lazy { PhotoFilterActivity::class.java.simpleName }
    private val permissionsManager by lazy { PermissionsManager(this) }

    companion object {
        private const val REQUEST_CODE_IMAGE = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.photo_activity)
        setActionBar()

        binding = DataBindingUtil.setContentView(this, R.layout.photo_activity)
        binding.lifecycleOwner = this

        setViewModel()
        setOnClickListeners()

    }

    private fun setActionBar() {
        supportActionBar?.let {
            title = getString(R.string.photo_features_title)
            it.setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun setViewModel() {
        filterViewModel = ViewModelProviders.of(this).get(PhotoFilterViewModel::class.java)
        filterViewModel.imageUri?.let {
            binding.filterImage.isEnabled = true
        }
        filterViewModel.outputUri.observe(this, Observer {
            it?.let { setImageView(it) }
        })
        filterViewModel.workInfoState.observe(this, Observer {
            it?.let { if (it) showWorkFinished() else showWorkInProgress() }
        })
        filterViewModel.workInfos.observe(this, Observer {
            it?.let { filterViewModel.onWorkInfosChanged(it) }
        })
    }

    private fun showWorkInProgress() {
        binding.progressBar.visibility = View.VISIBLE
        binding.cancelButton.visibility = View.VISIBLE
    }

    private fun showWorkFinished() {
        binding.progressBar.visibility = View.GONE
        binding.cancelButton.visibility = View.GONE
    }

    private fun setOnClickListeners() {
        binding.selectImage?.setOnClickListener {
            if (permissionsManager.checkAllPermissions()) {
                showImagePicker()
            } else {
                permissionsManager.requestPermissionsIfNecessary()
            }
        }
        binding.filterImage?.setOnClickListener {
            filterViewModel.applySepia()
        }
        binding.cancelButton?.setOnClickListener { filterViewModel.cancelWork() }
    }

    private fun showImagePicker() {

        val chooseIntent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        startActivityForResult(chooseIntent, REQUEST_CODE_IMAGE)
    }

    private fun handleImageRequestResult(intent: Intent) {
        val imageUri: Uri? = intent.clipData?.let {
            it.getItemAt(0).uri
        } ?: intent.data

        if (imageUri == null) {
            Log.d(TAG, "Invalid input image Uri.")
            return
        }
        setImageView(imageUri)
    }

    private fun setImageView(imageUri: Uri) {
        filterViewModel.setImageUri(imageUri.toString())
        filterViewModel.imageUri?.let {
            Glide.with(this).load(imageUri).centerCrop()
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        Log.d(TAG, "error loading image with Glide", e)
                        binding.imageView.visibility = View.GONE
                        binding.filterImage.isEnabled = false
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        Log.i(TAG, "success loading image with Glide")
                        binding.imageView.visibility = View.VISIBLE
                        binding.filterImage.isEnabled = true
                        return false
                    }
                }).into(binding.imageView)
        }
    }

    override fun onRequestPermissionsResult(
        code: Int,
        permissions: Array<String>,
        result: IntArray
    ) {
        super.onRequestPermissionsResult(code, permissions, result)

        when (code) {
            REQUEST_CODE_PERMISSIONS -> {
                if (result.isNotEmpty() && result[0] == PackageManager.PERMISSION_GRANTED) {
                    showImagePicker()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_CODE_IMAGE ->
                    data?.let {
                        handleImageRequestResult(data)
                    }
                else -> Log.d(TAG, "Unknown request code.")
            }
        } else {
            Log.d(TAG, String.format("Unexpected Result code %s", resultCode))
        }
    }
}