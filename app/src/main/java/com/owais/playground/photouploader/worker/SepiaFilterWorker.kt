package com.owais.playground.photouploader.worker

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.text.TextUtils
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.owais.playground.Constants.Companion.KEY_IMAGE_URI
import com.owais.playground.Utils

class SepiaFilterWorker(contextParam: Context, workerParams: WorkerParameters) :
    Worker(contextParam, workerParams) {

    private val TAG by lazy { SepiaFilterWorker::class.java.simpleName }

    override fun doWork(): Result {
        val appContext = applicationContext
        val resourceUri = inputData.getString(KEY_IMAGE_URI)
        makeStatusNotification("Sepia Filter image", appContext)

        return try {
            if (TextUtils.isEmpty(resourceUri)) {
                Log.d(TAG, "Invalid input uri)")
                throw IllegalArgumentException("Invalid input uri")
            }


            val resolver = appContext.contentResolver
            val picture = BitmapFactory.decodeStream(
                resolver.openInputStream(Uri.parse(resourceUri))
            )

            val output = Utils.applySepiaFilter(picture)
            val outputUri = writeBitmapToFile(appContext, output)
            val outputData = workDataOf(KEY_IMAGE_URI to outputUri.toString())
            Result.success(outputData)

        } catch (t: Throwable) {
            Log.d(TAG, "error doing sepia filter work", t)
            Result.failure()
        }
    }

//    private fun createOutputData(outputFilePath: String): Data {
//        return Data.Builder()
//            .putString(PhotoViewModel.OUTPUT_FILE_PATH, outputFilePath)
//            .build()
//    }
}