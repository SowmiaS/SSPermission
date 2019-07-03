package com.ss.sspermission

import android.Manifest
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.ss.sspermission.request.PermissionRequest
import com.ss.sspermission.response.PermissionResponseHandler

class MainActivity : AppCompatActivity(), PermissionResponseHandler {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestPermission()
    }

    private fun requestPermission() {
        val permissions: ArrayList<String> =
            ArrayList()
        permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        permissions.add(Manifest.permission.ACCESS_FINE_LOCATION)

        val request = PermissionRequest(this, permissions, this)
        PermissionHandler.request(request)
    }

    override fun onGranted(permissions: ArrayList<String>) {
        Log.d("SSPERMISSION", "Permission Granted")
        Toast.makeText(this, "Permission Granted", Toast.LENGTH_LONG).show()
    }

    override fun onDenied(permissions: ArrayList<String>) {
        Log.d("SSPERMISSION", "Permission Denied")
        Toast.makeText(this, "Permission Denied", Toast.LENGTH_LONG).show()
    }
}
