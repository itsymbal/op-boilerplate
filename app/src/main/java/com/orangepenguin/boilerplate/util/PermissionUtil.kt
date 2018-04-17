package com.orangepenguin.boilerplate.util

import android.Manifest
import android.app.Activity
import com.orangepenguin.boilerplate.screens.util.PermissionsDialogWithRationale
import com.orangepenguin.boilerplate.screens.util.PermissionsDialogWithSettingsLink
import com.orangepenguin.boilerplate.util.PermissionApi.Companion.anyPermissionDenied
import com.orangepenguin.boilerplate.util.PermissionApi.Companion.anyPermissionPermanentlyDenied
import com.orangepenguin.boilerplate.util.PermissionApi.Companion.requestPermissionsFromActivity

/**
 * This is a utility class to check permissions available to the current app.
 */
class PermissionUtil {
    companion object {
        /**
         * Request permissions. If any permission permanently denied
         * with "Don't ask again", send to settings screen. If already shown once, show rationale then
         * ask again. If have not shown yet, then just ask for permissions.
         */
        fun requestPermissionsShowingRationaleIfDeniedOnce(activity: Activity,
                                                           permissionRequestResultCode: Int,
                                                           rationaleMsgId: Int, allRequiredPermissions: Array<String>) {

            if (anyPermissionPermanentlyDenied(activity, * allRequiredPermissions)) {
                suggestUserEnablePermsInSettings(activity, rationaleMsgId)
            } else if (anyPermissionDenied(activity, * allRequiredPermissions)) {
                showRationaleThenRequestPermissions(activity, allRequiredPermissions,
                        permissionRequestResultCode, rationaleMsgId)
            } else {
                requestPermissionsFromActivity(activity, permissionRequestResultCode, * allRequiredPermissions)
            }
        }

        /**
         * Request permissions. If any permission permanently denied
         * with "Don't ask again", send to settings screen. Show rationale then ask for permissions.
         */
        fun requestPermissionsShowingRationale(activity: Activity,
                                               permissionRequestResultCode: Int,
                                               rationaleMsgId: Int, allRequiredPermissions: Array<String>) {

            if (anyPermissionPermanentlyDenied(activity, * allRequiredPermissions)) {
                suggestUserEnablePermsInSettings(activity, rationaleMsgId)
            } else {
                showRationaleThenRequestPermissions(activity, allRequiredPermissions,
                        permissionRequestResultCode, rationaleMsgId)
            }
        }

        /**
         * Request permissions. If any permission permanently denied
         * with "Don't ask again", send to settings screen. Show rationale then ask for permissions.
         */
        fun requestPermissions(activity: Activity, permissionRequestResultCode: Int,
                               rationaleMsgId: Int, allRequiredPermissions: Array<String>) {

            if (anyPermissionPermanentlyDenied(activity, * allRequiredPermissions)) {
                suggestUserEnablePermsInSettings(activity, rationaleMsgId)
            } else {
                requestPermissionsFromActivity(activity, permissionRequestResultCode, * allRequiredPermissions)
            }
        }


        private fun showRationaleThenRequestPermissions(activity: Activity,
                                                        permissions: Array<out String>, resultCode: Int,
                                                        rationaleResourceId: Int) {
            PermissionsDialogWithRationale.createInstance(rationaleResourceId, permissions, resultCode)
                    .show(activity.fragmentManager, null)
        }

        private fun suggestUserEnablePermsInSettings(activity: Activity, suggestionTextStringId: Int) {
            PermissionsDialogWithSettingsLink.createInstance(suggestionTextStringId)
                    .show(activity.fragmentManager, null)
        }

        val PERMS_LOCATION = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
        val PERMS_CAMERA = arrayOf(Manifest.permission.CAMERA)

        val RESULT_PERMS_LOCATION = 1
        val RESULT_PERMS_CAMERA = 2
    }
}
