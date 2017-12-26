package com.orangepenguin.boilerplate.util

class NotificationUtiConsoleImpl : NotificationUtil {
    override fun showToast(message: String, duration: Int) = println(message)
}
