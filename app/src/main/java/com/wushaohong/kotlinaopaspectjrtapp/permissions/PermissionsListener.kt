package com.wushaohong.kotlinaopaspectjrtapp.permissions

interface PermissionsListener {

    fun allow()
    fun refuse(permissions: Array<String>)

}