package com.ss.sspermission

import android.app.Activity
import android.content.pm.PackageManager
import android.support.v4.content.ContextCompat
import com.ss.sspermission.activity.PermissionHandlerActivity
import com.ss.sspermission.request.PermissionRequest

object PermissionHandler {

    private val REQUEST_CODE: Int = 344

    private var permissionRequest: PermissionRequest? = null


    fun request(perRequest: PermissionRequest) {
        this.permissionRequest = perRequest
        requestPermissions(permissionRequest!!.permissions, permissionRequest!!.activity)
    }

    private fun requestPermissions(permissions: ArrayList<String>, activity: Activity) {
        val permissionsNeedToBeRequested: ArrayList<String> = getPermissionNeeded(permissions)
        if (permissionsNeedToBeRequested.size == 0) {
            permissionRequest?.listener?.onGranted(permissions)
        } else {
            PermissionHandlerActivity.newIntent(activity, permissionsNeedToBeRequested.toTypedArray())
        }

    }

    fun handlePermissionResponse(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {

        when (requestCode) {
            REQUEST_CODE ->
                if (grantResults.isEmpty()) return
                else {

                    val permissionsDenied: ArrayList<String> = ArrayList()
                    val permissionsGranted: ArrayList<String> = ArrayList()

                    for ((index, granted) in grantResults.withIndex()) {
                        if (granted == PackageManager.PERMISSION_GRANTED) permissionsGranted.add(permissions[index])
                        if (granted == PackageManager.PERMISSION_DENIED) permissionsDenied.add(permissions[index])
                    }

                    if (permissionsGranted.size != 0) {
                        PermissionHandler.permissionRequest!!.listener.onGranted(permissionsGranted)
                    }
                    if (permissionsDenied.size != 0) {
                        PermissionHandler.permissionRequest!!.listener.onDenied(permissionsDenied)
                    }
                }
        }

    }

    private fun getPermissionNeeded(permissions: ArrayList<String>): ArrayList<String> {
        val permissionsNeedToBeRequested: ArrayList<String> = ArrayList()
        var index = 0
        for (permission: String in permissions) {
            if (permissionRequest != null) {
                if (!isEnabled(permission, permissionRequest?.activity!!)) {
                    permissionsNeedToBeRequested.add(permission)
                    index++
                }

            }
        }
        return permissionsNeedToBeRequested
    }

    private fun isEnabled(permission: String, activity: Activity): Boolean {
        return (ContextCompat.checkSelfPermission(activity, permission)
                == PackageManager.PERMISSION_GRANTED)
    }

}