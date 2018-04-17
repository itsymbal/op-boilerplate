package com.orangepenguin.boilerplate.util

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.support.v4.app.ActivityCompat

class PermissionApi {
    companion object {
        private const val PERMISSIONS_PREFS = "PERMISSIONS_PREFS"
        private const val PREF_HAS_REQUESTED_PERM = "PREF_HAS_REQUESTED_PERM_"

        /**
         * Show if all supplied permissions have been granted
         */
        fun permissionsGranted(context: Context, permissions: Array<String>): Boolean {
            return permissions.all { permissionGranted(context, it) }
        }

        /**
         * Show if supplied permission has been granted
         */
        fun permissionGranted(context: Context, permission: String): Boolean =
                ActivityCompat.checkSelfPermission(context, permission) == PERMISSION_GRANTED

        /**
         * Show if at least one of the supplied permissions has been denied
         */
        fun anyPermissionDenied(activity: Activity, vararg permissions: String): Boolean {
            return permissions.any { permissionDenied(activity, it) }
        }

        /**
         * Show if at the supplied permission has been denied
         */
        fun permissionDenied(activity: Activity, permission: String): Boolean =
                ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)

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
                    !ActivityCompat.shouldShowRequestPermissionRationale(activity, permission) &&
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
        fun requestPermissions(activity: Activity, permRequestResultCode: Int, vararg allRequiredPermissions: String) {
            ActivityCompat.requestPermissions(activity, getNotGrantedPermissions(activity, allRequiredPermissions),
                    permRequestResultCode)
        }

        private fun hasRequestedPermission(context: Context, permission: String): Boolean =
                getPreference(context, PREF_HAS_REQUESTED_PERM + permission, false)

        private fun getNotGrantedPermissions(activity: Activity, wanted: Array<out String>): Array<String> {
            return wanted.filterNot { permissionGranted(activity, it) }.toTypedArray()
        }

        private fun savePreference(context: Context, key: String, value: Boolean) =
                context.getSharedPreferences(PERMISSIONS_PREFS, Context.MODE_PRIVATE).edit().putBoolean(key, value).apply()

        private fun getPreference(context: Context, key: String, defaultValue: Boolean): Boolean =
                context.getSharedPreferences(PERMISSIONS_PREFS, Context.MODE_PRIVATE).getBoolean(key, defaultValue)
    }
}
