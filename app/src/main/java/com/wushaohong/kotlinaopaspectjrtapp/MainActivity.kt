package com.wushaohong.kotlinaopaspectjrtapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.wushaohong.kotlinaopaspectjrtapp.click.CheckPoint
import com.wushaohong.kotlinaopaspectjrtapp.permissions.PermissionsCheck

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    @CheckPoint("AOP Click")
    fun onClick(view: View) {
        Log.e("wush", "onClick")
    }

    @PermissionsCheck([android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.CALL_PHONE])
    fun onCheckPermissions(view: View) {
        Log.e("wush", "onCheckPermissions")
    }
}
