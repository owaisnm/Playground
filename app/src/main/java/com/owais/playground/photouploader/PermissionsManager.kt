package com.owais.playground.photouploader

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.owais.playground.R
import com.owais.playground.Utils
import java.util.*

class PermissionsManager(ctx: Activity) {
    private val context = ctx
    private val TAG by lazy { PermissionsManager::class.java.simpleName }

    companion object {
        const val REQUEST_CODE_PERMISSIONS = 101

        private val PERMISSIONS = Arrays.asList(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
    }

    fun checkAllPermissions(): Boolean {
        var hasPermissions = true
        for (permission in PERMISSIONS) {
            hasPermissions = hasPermissions and isPermissionGranted(permission)
        }
        return hasPermissions
    }

    private fun isPermissionGranted(permission: String) =
        ContextCompat.checkSelfPermission(context, permission) ==
                PackageManager.PERMISSION_GRANTED

    fun requestPermissionsIfNecessary() {
        if (Utils.hasMarshmellow()) {
            ActivityCompat.requestPermissions(
                context,
                PERMISSIONS.toTypedArray(),
                REQUEST_CODE_PERMISSIONS
            )

        } else {
            Log.i(
                TAG,
                "storage read or write permissions have NOT been granted"
            )
            Toast.makeText(context, R.string.need_permissions, Toast.LENGTH_SHORT).show()
        }
    }
}