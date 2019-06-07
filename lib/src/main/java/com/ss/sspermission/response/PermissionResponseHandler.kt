package com.ss.sspermission.response

interface PermissionResponseHandler {

    fun onGranted(permissions: ArrayList<String>)

    fun onDenied(permissions: ArrayList<String>)

}