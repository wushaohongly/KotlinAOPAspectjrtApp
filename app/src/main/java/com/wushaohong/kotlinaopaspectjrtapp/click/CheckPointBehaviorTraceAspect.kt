package com.wushaohong.kotlinaopaspectjrtapp.click

import android.util.Log
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.aspectj.lang.reflect.MethodSignature

@Aspect
class CheckPointBehaviorTraceAspect {

    @Pointcut("execution(@com.wushaohong.kotlinaopaspectjrtapp.click.CheckPoint * *(..))")
    fun methodAnnotated() {
    }

    @Around("methodAnnotated()")
    @Throws(Throwable::class)
    fun aroundJoinPoint(joinPoint: ProceedingJoinPoint) {

        Log.e("wush", "aroundJoinPoint")

        val methodSignature = joinPoint.signature as MethodSignature
        val method = methodSignature.method

        if (!method.isAnnotationPresent(CheckPoint::class.java)) {
            return
        }

        val annotation = method.getAnnotation(CheckPoint::class.java) ?: return
        val text = annotation.value

        Log.e("wush", text)

        joinPoint.proceed()
    }
}