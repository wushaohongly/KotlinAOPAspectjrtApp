package com.wushaohong.kotlinaopaspectjrtapp.permissions

import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.content.ContextCompat
import com.wushaohong.kotlinaopaspectjrtapp.dialog.PermissionRefuseDialog
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.aspectj.lang.reflect.MethodSignature
import java.util.*

@Aspect
class PermissionsCheckBehaviorTraceAspect {

    @Pointcut("execution(@com.wushaohong.kotlinaopaspectjrtapp.permissions.PermissionsCheck * *(..))")
    fun methodAnnotated() {
    }

    /**
     * 环绕切入点
     */
    @Around("methodAnnotated()")
    @Throws(Throwable::class)
    fun aroundJoinPoint(joinPoint: ProceedingJoinPoint) {

        Log.e("wush", "aroundJoinPoint")

        // 获取方法
        val methodSignature = joinPoint.signature as MethodSignature
        val method = methodSignature.method

        if (!method.isAnnotationPresent(PermissionsCheck::class.java)) {
            return
        }

        // 获取注解
        val annotation = method.getAnnotation(PermissionsCheck::class.java) ?: return
        // 获取注解参数
        val permissions = annotation.array

        // 上下文
        val context = joinPoint.`this` as Context

        val afterCheckPermissions = ArrayList<String>()

        // 检查权限
        permissions.forEach {
            if (PackageManager.PERMISSION_DENIED == ContextCompat.checkSelfPermission(context, it)) {
                afterCheckPermissions.add(it)
            }
        }

        // 需要申请的权限为空，放行
        if (afterCheckPermissions.isEmpty()) {
            joinPoint.proceed()
            return
        }

        val permissionRefuseDialog = PermissionRefuseDialog(context)

        // 启动申请权限 Activity
        PermissionsRequestActivity.startActivity(context,
            afterCheckPermissions.toTypedArray(), object : PermissionsListener {

                override fun allow() {
                    Log.e("wush", "全部允许")
                    joinPoint.proceed()
                }

                override fun refuse(permissions: Array<String>) {
                    permissions.forEach {
                        Log.e("wush", "拒绝：$it")
                    }
                    permissionRefuseDialog.setPermissions(permissions)
                    permissionRefuseDialog.show()
                }

            })
    }

}