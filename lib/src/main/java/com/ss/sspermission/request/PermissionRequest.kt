package com.ss.sspermission.request

import android.support.v7.app.AppCompatActivity
import com.ss.sspermission.response.PermissionResponseHandler

data class PermissionRequest(
    val activity: AppCompatActivity,
    val permissions: ArrayList<String>,
    val listener: PermissionResponseHandler
)
