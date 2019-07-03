package com.ss.sspermission.request

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import com.ss.sspermission.response.PermissionResponseHandler

data class PermissionRequest(
    val activity: Activity,
    val permissions: ArrayList<String>,
    val listener: PermissionResponseHandler
)
