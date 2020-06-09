package com.wushaohong.kotlinaopaspectjrtapp.permissions

/**
 * 权限注解
 * 参数类型：权限数组
 */
@Target(AnnotationTarget.FUNCTION)
annotation class PermissionsCheck (
    val array : Array<String>
)