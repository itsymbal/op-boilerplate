package com.orangepenguin.boilerplate.util

import android.content.Context
import android.widget.Toast

class AndroidNotificationUtil(private val context: Context) : NotificationUtil {

    override fun showToast(message: String, duration: Int) = Toast.makeText(context, message, duration).show()
}
