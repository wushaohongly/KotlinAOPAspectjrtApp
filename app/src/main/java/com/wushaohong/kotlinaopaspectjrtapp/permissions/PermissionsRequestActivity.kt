package com.wushaohong.kotlinaopaspectjrtapp.permissions

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.core.app.ActivityCompat
import com.wushaohong.kotlinaopaspectjrtapp.R

/**
 * 申请权限 Activity，1px，透明，style对话框方式
 */
class PermissionsRequestActivity : Activity() {

    companion object {

        private const val INTENT_KEY_BUNDLE = "intent_key_bundle"
        private const val INTENT_KEY_PERMISSIONS = "intent_key_permissions"
        private var sPermissionsListener: PermissionsListener ?= null

        fun startActivity(context: Context, permissions: Array<String>, permissionsListener: PermissionsListener) {

            sPermissionsListener = permissionsListener

            val intent = Intent(context, PermissionsRequestActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)

            val bundle = Bundle()
            bundle.putStringArray(INTENT_KEY_PERMISSIONS, permissions)
            intent.putExtra(INTENT_KEY_BUNDLE, bundle)

            context.startActivity(intent)
        }
    }

    private var permissions : Array<String> ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_permission)

        val bundle = intent.getBundleExtra(INTENT_KEY_BUNDLE) ?: return

        permissions = bundle.getStringArray(INTENT_KEY_PERMISSIONS)

        permissions?.let {
            ActivityCompat.requestPermissions(this, it, 0)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        val deniedArray = ArrayList<String>()

        for ((index, e) in grantResults.withIndex()) {
            if (PackageManager.PERMISSION_DENIED == e) {
                deniedArray.add(permissions[index])
            }
        }

        if (deniedArray.isEmpty()) {
            sPermissionsListener?.allow()
        } else {
            sPermissionsListener?.refuse(deniedArray.toTypedArray())
        }

        finish()
    }

}