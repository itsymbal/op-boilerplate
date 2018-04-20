package com.orangepenguin.boilerplate.util

import android.app.Activity
import com.orangepenguin.boilerplate.screens.util.PermissionsDialogWithRationale
import com.orangepenguin.boilerplate.screens.util.PermissionsDialogWithSettingsLink
import com.orangepenguin.boilerplate.util.PermissionApi.Companion.anyPermissionDenied
import com.orangepenguin.boilerplate.util.PermissionApi.Companion.anyPermissionPermanentlyDenied
import com.orangepenguin.boilerplate.util.PermissionApi.Companion.requestPermissions

/**
 * Utility class to request permissions.
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
                                                           rationaleMsgId: Int, vararg allRequiredPermissions: String) {
            when {
                anyPermissionPermanentlyDenied(activity, * allRequiredPermissions) ->
                    suggestUserEnablePermsInSettings(activity, rationaleMsgId)

                anyPermissionDenied(activity, * allRequiredPermissions) ->
                    showRationaleThenRequestPermissions(activity, allRequiredPermissions, permissionRequestResultCode,
                            rationaleMsgId)

                else -> requestPermissions(activity, permissionRequestResultCode, * allRequiredPermissions)
            }
        }

        /**
         * Request permissions. If any permission permanently denied
         * with "Don't ask again", send to settings screen. Show rationale then ask for permissions.
         */
        fun requestPermissionsShowingRationale(activity: Activity,
                                               permissionRequestResultCode: Int,
                                               rationaleMsgId: Int, allRequiredPermissions: Array<String>) {
            when {
                anyPermissionPermanentlyDenied(activity, * allRequiredPermissions) ->
                    suggestUserEnablePermsInSettings(activity, rationaleMsgId)

                else -> showRationaleThenRequestPermissions(activity, allRequiredPermissions,
                        permissionRequestResultCode, rationaleMsgId)
            }
        }

        /**
         * Request permissions. If any permission permanently denied  with "Don't ask again", send to settings screen.
         * Otherwise show rationale then ask for permissions.
         */
        fun requestPermissions(activity: Activity, permissionRequestResultCode: Int,
                               rationaleMsgId: Int, allRequiredPermissions: Array<String>) {
            when {
                anyPermissionPermanentlyDenied(activity, * allRequiredPermissions) ->
                    suggestUserEnablePermsInSettings(activity, rationaleMsgId)

                else -> requestPermissions(activity, permissionRequestResultCode, * allRequiredPermissions)
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
    }
}
