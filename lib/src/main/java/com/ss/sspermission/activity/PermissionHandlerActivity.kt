package com.ss.sspermission.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager
import com.ss.sspermission.PermissionHandler

/**
 * This activity helps to decouple activity which uses the permission.
 * Permission is requested and permission response is received by this activity.
 * This activity sits on top of current activity with non touchable mode
 * and gets killed once permission response is processed.
 */
class PermissionHandlerActivity : AppCompatActivity() {


    companion object {

        val KEY_LIST_OF_PERMISSION_TOBE_REQUESTED: String = "KEY_LIST_OF_PERMISSION_TOBE_REQUESTED"

        fun newIntent(activity: Activity, permissions: Array<String>) {
            val intent = Intent(activity, PermissionHandlerActivity::class.java)
            intent.putExtra(KEY_LIST_OF_PERMISSION_TOBE_REQUESTED, permissions)
            activity.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        val permissions: Array<String>? =
            intent.getStringArrayExtra(KEY_LIST_OF_PERMISSION_TOBE_REQUESTED) as? Array<String>
        permissions?.let {
            PermissionHandler.callRequestPermissions(this, permissions)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        PermissionHandler.handlePermissionResponse(requestCode, permissions, grantResults)
        finish()
    }

}