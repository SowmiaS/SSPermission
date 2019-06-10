package com.ss.sspermission

import android.app.Activity
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import com.ss.sspermission.activity.PermissionHandlerActivity
import com.ss.sspermission.request.PermissionRequest

object PermissionHandler {

    val REQUEST_CODE: Int = 344

    private var permissionRequest: PermissionRequest? = null


    /**
     * This method requests the permissions
     */
    fun request(perRequest: PermissionRequest) {
        this.permissionRequest = perRequest
        permissionRequest?.let {
            checkPermissions(it.permissions)
        }
    }

    /**
     * This method handles the permission response from the android system
     */
    fun handlePermissionResponse(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {

        when (requestCode) {
            REQUEST_CODE -> {
                if (grantResults.isEmpty()) return

                val permissionsDenied: ArrayList<String> = ArrayList()
                val permissionsGranted: ArrayList<String> = ArrayList()

                for ((index, granted) in grantResults.withIndex()) {
                    if (granted == PackageManager.PERMISSION_GRANTED) permissionsGranted.add(permissions[index])
                    else if (granted == PackageManager.PERMISSION_DENIED) permissionsDenied.add(permissions[index])
                }
                callBackPermissionRequestor(permissionsGranted, permissionsDenied)
            }
        }

    }


    /**
     * This method handles the permission request to be done with android system.
     */
    fun callRequestPermissions(activity: PermissionHandlerActivity, permissions: Array<String>) {
        ActivityCompat.requestPermissions(activity, permissions, REQUEST_CODE)
    }

    /**
     * This method checks if the permissions is already granted and sends the permission that need to be requested to android system.
     */
    private fun checkPermissions(permissions: ArrayList<String>) {
        val permissionsNeedToBeRequested: ArrayList<String> =
            filterDisabledPermissions(permissions, permissionRequest?.activity)
        if (permissionsNeedToBeRequested.isEmpty()) {
            permissionRequest?.listener?.onGranted(permissions)
        } else {
            callPermissionHanlderActivity(permissionsNeedToBeRequested, permissionRequest?.activity)
        }
    }

    /**
     * This method calls the permission handler activity for sending the permission request to Android system
     */
    private fun callPermissionHanlderActivity(permissions: ArrayList<String>, activity: AppCompatActivity?) {
        activity?.let {
            PermissionHandlerActivity.newIntent(it, permissions.toTypedArray())
        }
    }


    /***
     * This method sends callback to permission requestor class with permission granted and denied list.
     */
    private fun callBackPermissionRequestor(
        permissionsGranted: ArrayList<String>,
        permissionsDenied: ArrayList<String>
    ) {
        if (permissionsGranted.isNotEmpty()) {
            PermissionHandler.permissionRequest?.listener?.onGranted(permissionsGranted)
        }
        if (permissionsDenied.isNotEmpty()) {
            PermissionHandler.permissionRequest?.listener?.onDenied(permissionsDenied)
        }
    }

    /**
     * This method filters out disabled permissions from the given list of permissions
     */
    private fun filterDisabledPermissions(permissions: ArrayList<String>, activity: AppCompatActivity?): ArrayList<String> {
        val iter : MutableIterator<String>  = permissions.iterator();

        while(iter.hasNext()) {
            activity?.let {
                if (isPermissionEnabled(iter.next(), activity)) {
                    iter.remove()
                }
            }
        }
        return permissions
    }

    /**
     * This method checks if the given permission is granted.
     * return true if GRANTED
     *        false if DENIED
     */
    private fun isPermissionEnabled(permission: String, activity: Activity): Boolean {
        return (ContextCompat.checkSelfPermission(activity, permission)
                == PackageManager.PERMISSION_GRANTED)
    }


}