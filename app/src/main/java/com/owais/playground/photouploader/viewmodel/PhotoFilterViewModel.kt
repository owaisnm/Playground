package com.owais.playground.photouploader.viewmodel

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.work.*
import com.owais.playground.Constants.Companion.IMAGE_MANIPULATION_WORK_NAME
import com.owais.playground.Constants.Companion.KEY_IMAGE_URI
import com.owais.playground.Constants.Companion.TAG_OUTPUT
import com.owais.playground.photouploader.worker.CleanupWorker
import com.owais.playground.photouploader.worker.SaveImageToFileWorker
import com.owais.playground.photouploader.worker.SepiaFilterWorker

class PhotoFilterViewModel(application: Application) : AndroidViewModel(application) {

    var imageUri: Uri? = null

    private val _outputUri = MutableLiveData<Uri>()
    var outputUri: LiveData<Uri>
    var _workInfoState = MutableLiveData<Boolean>()//.apply { true }
    var workInfoState: LiveData<Boolean>
    var workInfos: LiveData<List<WorkInfo>>
    private val workManager = WorkManager.getInstance(application)

    init {
        workInfoState = _workInfoState
        outputUri = _outputUri
        workInfos = workManager.getWorkInfosByTagLiveData(TAG_OUTPUT)
    }

    fun onWorkInfosChanged(workInfos: List<WorkInfo>) {
        if (workInfos.isNullOrEmpty()) {
            return
        }

        val workInfo = workInfos[0]
        if (workInfo.state.isFinished) {
            _workInfoState.postValue(true)
            val outputImageUri = workInfo.outputData.getString(KEY_IMAGE_URI)
            if (!outputImageUri.isNullOrEmpty()) {
                _outputUri.postValue(uriOrNull(outputImageUri))
            }
        } else {
            _workInfoState.postValue(false)
        }
    }

    internal fun applySepia() {

        // chain work ensuring unique
        var continuation = workManager
            .beginUniqueWork(
                IMAGE_MANIPULATION_WORK_NAME,
                ExistingWorkPolicy.REPLACE,
                OneTimeWorkRequest.from(CleanupWorker::class.java)
            )
        val sepiaRequest = OneTimeWorkRequest.Builder(SepiaFilterWorker::class.java)
            .setInputData(createInputDataForUri())
            .build()

        continuation = continuation.then(sepiaRequest)
        val save =
            OneTimeWorkRequest.Builder(SaveImageToFileWorker::class.java).addTag(TAG_OUTPUT).build()
        continuation = continuation.then(save)
        continuation.enqueue()

    }

    private fun createInputDataForUri(): Data {
        val builder = Data.Builder()
        imageUri?.let {
            builder.putString(KEY_IMAGE_URI, imageUri.toString())
        }
        return builder.build()
    }


    internal fun setImageUri(uri: String?) {
        imageUri = uriOrNull(uri)
    }

    internal fun cancelWork() {
        workManager.cancelUniqueWork(IMAGE_MANIPULATION_WORK_NAME)
    }

    private fun uriOrNull(uriString: String?): Uri? {
        return if (!uriString.isNullOrEmpty()) {
            Uri.parse(uriString)
        } else {
            null
        }
    }

}