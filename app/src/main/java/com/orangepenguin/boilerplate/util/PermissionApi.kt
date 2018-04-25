package com.orangepenguin.boilerplate.util

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.support.v4.app.ActivityCompat


object PermissionApi {
    private const val PERMISSIONS_PREFS = "PERMISSIONS_PREFS"
    private const val PREF_HAS_REQUESTED_PERM = "PREF_HAS_REQUESTED_PERM_"

    var systemApiInterface: SystemApiInterface = AndroidApiInterface()

    /**
     * Show if all supplied permissions have been granted
     */
    fun permissionsGranted(context: Context, vararg permissions: String): Boolean =
            permissions.all { permissionGranted(context, it) }

    /**
     * Show if supplied permission has been granted
     */
    fun permissionGranted(context: Context, permission: String): Boolean =
            systemApiInterface.checkSelfPermission(context, permission) == PERMISSION_GRANTED

    /**
     * Show if at least one of the supplied permissions has been denied
     */
    fun anyPermissionDenied(activity: Activity, vararg permissions: String): Boolean =
            permissions.any { permissionDenied(activity, it) }

    /**
     * Show if at the supplied permission has been denied
     */
    fun permissionDenied(activity: Activity, permission: String): Boolean =
            systemApiInterface.shouldShowRequestPermissionRationale(activity, permission)

    /**
     * Show if any of the supplied permissions has been permanently denied
     */
    fun anyPermissionPermanentlyDenied(activity: Activity, vararg permissions: String): Boolean =
            permissions.any { permissionPermanentlyDenied(activity, it) }

    /**
     * Show if permission has been permanently denied
     */
    fun permissionPermanentlyDenied(activity: Activity, permission: String): Boolean =
            !permissionGranted(activity, permission) &&
                    !systemApiInterface.shouldShowRequestPermissionRationale(activity, permission) &&
                    hasRequestedPermission(activity, permission)

    /**
     * Record the fact that permission has been requested. Used later to tell that it's been denied.
     */
    fun setPermissionRequestedFlag(context: Context, permissions: Array<String>) {
        permissions.forEach { permission -> savePreference(context, PREF_HAS_REQUESTED_PERM + permission, true) }
    }

    /**
     * Request permissions which haven't yet been granted
     */
    fun requestPermissions(activity: Activity, requestResultCode: Int, vararg allRequiredPermissions: String) {
        systemApiInterface.requestPermissions(activity, getNotGrantedPermissions(activity, allRequiredPermissions),
                requestResultCode)
    }

    private fun hasRequestedPermission(context: Context, permission: String): Boolean =
            getPreference(context, PREF_HAS_REQUESTED_PERM + permission, false)

    private fun getNotGrantedPermissions(activity: Activity, wanted: Array<out String>): Array<String> {
        return wanted.filterNot { permissionGranted(activity, it) }.toTypedArray()
    }

    private fun savePreference(context: Context, key: String, value: Boolean) =
            systemApiInterface.savePreference(context, key, value)

    private fun getPreference(context: Context, key: String, defaultValue: Boolean): Boolean =
            systemApiInterface.getPreference(context, key, defaultValue)

    interface SystemApiInterface {
        fun shouldShowRequestPermissionRationale(activity: Activity, permission: String): Boolean
        fun checkSelfPermission(context: Context, permission: String): Int
        fun requestPermissions(activity: Activity, permissions: Array<String>, requestResultCode: Int)
        fun savePreference(context: Context, key: String, value: Boolean)
        fun getPreference(context: Context, key: String, defaultValue: Boolean): Boolean
    }

    class AndroidApiInterface : SystemApiInterface {
        override fun shouldShowRequestPermissionRationale(activity: Activity, permission: String) =
                ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)

        override fun checkSelfPermission(context: Context, permission: String) =
                ActivityCompat.checkSelfPermission(context, permission)

        override fun requestPermissions(activity: Activity, permissions: Array<String>, requestResultCode: Int) =
                ActivityCompat.requestPermissions(activity, permissions, requestResultCode)

        override fun savePreference(context: Context, key: String, value: Boolean) =
                context.getSharedPreferences(PERMISSIONS_PREFS, Context.MODE_PRIVATE).edit().putBoolean(key, value).apply()

        override fun getPreference(context: Context, key: String, defaultValue: Boolean): Boolean =
                context.getSharedPreferences(PERMISSIONS_PREFS, Context.MODE_PRIVATE).getBoolean(key, defaultValue)
    }
}
