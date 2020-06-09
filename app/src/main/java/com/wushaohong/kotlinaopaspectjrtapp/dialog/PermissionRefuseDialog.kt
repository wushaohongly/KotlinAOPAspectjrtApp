package com.wushaohong.kotlinaopaspectjrtapp.dialog

import android.app.Dialog
import android.content.Context
import com.wushaohong.kotlinaopaspectjrtapp.R
import kotlinx.android.synthetic.main.layout_permissions_dialog.*

/**
 * 权限拒绝对话框
 */
class PermissionRefuseDialog(context: Context) :
    Dialog(context) {

    private var permissions: Array<String> ?= null

    init {
        setContentView(R.layout.layout_permissions_dialog)
        bt_cancel_permission_dialog.setOnClickListener { dismiss() }
        bt_open_permission_dialog.setOnClickListener { dismiss() }
        setCancelable(true)
    }

    fun setPermissions(permissions: Array<String>) {
        this.permissions = permissions
        val stringBuilder = StringBuilder()
        for (i in permissions.indices) {
            val p = permissions[i]
            stringBuilder.append(p)
            if (i != permissions.size - 1) {
                stringBuilder.append("\n")
            }
        }
        tv_content_permission_dialog.text = stringBuilder.toString()
    }
}