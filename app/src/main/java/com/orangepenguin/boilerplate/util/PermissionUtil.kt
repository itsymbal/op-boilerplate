package com.orangepenguin.boilerplate.util

import android.Manifest
import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager.PERMISSION_DENIED
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.Build
import android.support.v4.app.ActivityCompat
import com.orangepenguin.boilerplate.screens.util.PermissionsDialogWithRationale
import com.orangepenguin.boilerplate.screens.util.PermissionsDialogWithSettingsLink
import javax.inject.Inject

/**
 * This is a utility class to check permissions available to the current app.
 */
class PermissionUtil @Inject
constructor(private val sharedPreferencesUtil: SharedPreferencesUtil,
            private val context: Context) {

    /**
     * Check to see if app already has given set of permissions granted.
     *
     * @return true if all of the supplied permissions are granted or if running on pre-M device.
     */
    fun hasPermissions(permissions: Array<String>): Boolean {
        return permissions.all { hasPermission(context, it) }
    }

    /**
     * Utility method to show if app already has or should even bother requesting given permissions.
     *
     * @return true if all permissions are either already granted or could be granted
     */
    private fun hasOrCouldPossiblyObtainPermissions(activity: Activity,
                                                    permissions: Array<String>): Boolean =
            permissions.all { hasOrCouldPossiblyObtainPermission(activity, it) }

    /**
     * Check if app already has or can possibly obtain one given permission.
     *
     * @return true if app already has or should even bother requesting permission or on pre-M
     * device.
     */
    private fun hasOrCouldPossiblyObtainPermission(activity: Activity,
                                                   permission: String): Boolean {
        return hasPermission(activity, permission) ||
                ActivityCompat.shouldShowRequestPermissionRationale(activity, permission) ||
                !hasRequestedPermission(permission)
    }

    /**
     * Request permission in the most appropriate way possible. If permission permanently denied
     * with "Don't ask again", send to settings screen. If already shown once, show rationale then
     * ask again. If have not shown yet, then just ask for permission.
     */
    fun requestPermissionsShowingRationaleIfDeniedOnce(activity: Activity,
                                                       allRequiredPermissions: Array<String>,
                                                       permissionRequestResultCode: Int,
                                                       rationaleMsgId: Int) {
        if (!hasOrCouldPossiblyObtainPermissions(activity, allRequiredPermissions)) {
            suggestUserEnablePermsInSettings(activity, rationaleMsgId)
        } else if (shouldShowRationale(activity, allRequiredPermissions)) {
            showRationaleThenRequestPermissions(activity, allRequiredPermissions,
                    permissionRequestResultCode, rationaleMsgId)
        } else {
            requestPermissionsFromActivity(activity, allRequiredPermissions,
                    permissionRequestResultCode)
        }
    }

    fun setPermissionRequestedFlag(permissions: Array<String>) {
        for (permission1 in permissions) {
            sharedPreferencesUtil.savePreference(PREF_HAS_REQUESTED_PERM + permission1, true)
        }
    }

    private fun hasRequestedPermission(permission: String): Boolean =
            sharedPreferencesUtil.getPreference(PREF_HAS_REQUESTED_PERM + permission, false)

    companion object {
        val PERMS_LOCATION = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION)
        val PERMS_CAMERA = arrayOf(Manifest.permission.CAMERA)

        val RESULT_PERMS_LOCATION = 1
        val RESULT_PERMS_CAMERA = 2

        private val PREF_HAS_REQUESTED_PERM = "PREF_HAS_REQUESTED_PERM_"

        /**
         * @param context Application context.
         * @param permissionName Name of the permission to check.
         * @return True if the application is granted `permissionName`.
         */
        fun isPermissionGranted(context: Context, permissionName: String): Boolean {
            return arePermissionsGranted(context, permissionName)
        }

        /**
         * @param context Application context.
         * @param permissionNames Array of names of the permission to check.
         * @return True if the application is granted `permissionName`.
         */
        fun arePermissionsGranted(context: Context, vararg permissionNames: String): Boolean {
            val pm = context.packageManager
            val packageName = context.packageName

            return permissionNames.none { pm.checkPermission(it, packageName) == PERMISSION_DENIED }
        }

        fun requestPermissionsFromActivity(activity: Activity,
                                           allRequiredPermissions: Array<String>,
                                           permRequestResultCode: Int) {
            ActivityCompat.requestPermissions(activity,
                    getNotGrantedPermissions(activity, allRequiredPermissions),
                    permRequestResultCode)
        }

        @TargetApi(Build.VERSION_CODES.M)
        fun hasPermission(context: Context, permission: String): Boolean {
            return if (!usesRuntimePermissions()) {
                true
            } else ActivityCompat.checkSelfPermission(context,
                    permission) == PERMISSION_GRANTED
        }

        private fun usesRuntimePermissions(): Boolean {
            return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
        }

        /**
         * show if at least one of the permissions should have its rationale shown
         */
        @TargetApi(Build.VERSION_CODES.M)
        private fun shouldShowRationale(activity: Activity,
                                        permissions: Array<String>): Boolean {
            if (usesRuntimePermissions()) {
                for (permission in permissions) {
                    if (activity.shouldShowRequestPermissionRationale(permission)) {
                        return true
                    }
                }
            }
            return false
        }

        private fun getNotGrantedPermissions(activity: Activity,
                                             wanted: Array<String>): Array<String> {
            return wanted.filterNot { hasPermission(activity, it) }.toTypedArray()
        }

        private fun showRationaleThenRequestPermissions(activity: Activity,
                                                        permissions: Array<String>, resultCode: Int,
                                                        rationaleResourceId: Int) {
            PermissionsDialogWithRationale.createInstance(rationaleResourceId, permissions,
                    resultCode)
                    .show(activity.fragmentManager, null)
        }

        private fun suggestUserEnablePermsInSettings(activity: Activity,
                                                     suggestionTextStringId: Int) {
            PermissionsDialogWithSettingsLink.createInstance(suggestionTextStringId)
                    .show(activity.fragmentManager, null)
        }
    }
}
