package com.orangepenguin.boilerplate.util

import android.app.Activity
import com.orangepenguin.boilerplate.screens.util.PermissionsDialogWithRationale
import com.orangepenguin.boilerplate.screens.util.PermissionsDialogWithSettingsLink

/**
 * Utility class to request permissions.
 */
object PermissionUtil {

    var permissionApi = PermissionApi

    fun requestPermissions(strategy: PermissionRequestStrategy) {
        strategy.requestPermissions()
    }

    fun showRationaleThenRequestPermissions(activity: Activity, permissions: Array<out String>, resultCode: Int,
            rationaleResourceId: Int) {
        PermissionsDialogWithRationale.createInstance(rationaleResourceId, permissions, resultCode)
                .show(activity.fragmentManager, null)
    }

    fun suggestUserEnablePermsInSettings(activity: Activity, suggestionTextStringId: Int) {
        PermissionsDialogWithSettingsLink.createInstance(suggestionTextStringId).show(activity.fragmentManager, null)
    }
}

interface PermissionRequestStrategy {
    fun requestPermissions()
}

/**
 * Request permissions. If any permission permanently denied  with "Don't ask again", send to settings screen. If
 * already shown once, show rationale then ask again. If have not shown yet, then just ask for permissions.
 */
class ShowRationaleIfDenied(val activity: Activity, private val permissionRequestCode: Int,
        private val rationaleMsgId: Int, private vararg val allRequiredPermissions: String) :
        PermissionRequestStrategy {
    override fun requestPermissions() {
        when {
            PermissionUtil.permissionApi.anyPermissionPermanentlyDenied(activity, * allRequiredPermissions) ->
                PermissionUtil.suggestUserEnablePermsInSettings(activity, rationaleMsgId)

            PermissionUtil.permissionApi.anyPermissionDenied(activity, * allRequiredPermissions) ->
                PermissionUtil.showRationaleThenRequestPermissions(activity, allRequiredPermissions,
                        permissionRequestCode, rationaleMsgId)

            else -> PermissionUtil.permissionApi.requestPermissions(activity, permissionRequestCode, *
            allRequiredPermissions)
        }
    }
}

/**
 * Request permissions. If any permission permanently denied with "Don't ask again", send to settings screen.
 * Otherwise, show rationale then ask for permissions.
 */
class AlwaysShowRationale(val activity: Activity, private val permissionRequestCode: Int,
        private val rationaleMsgId: Int, private vararg val allRequiredPermissions: String) :
        PermissionRequestStrategy {
    override fun requestPermissions() {
        when {
            PermissionUtil.permissionApi.anyPermissionPermanentlyDenied(activity, * allRequiredPermissions) ->
                PermissionUtil.suggestUserEnablePermsInSettings(activity, rationaleMsgId)

            else -> PermissionUtil.showRationaleThenRequestPermissions(activity, allRequiredPermissions,
                    permissionRequestCode, rationaleMsgId)
        }
    }
}

/**
 * Request permissions. If any permission permanently denied  with "Don't ask again", send to settings screen.
 * Otherwise show rationale then ask for permissions.
 */
class NeverShowRationale(val activity: Activity, private val permissionRequestCode: Int,
        private val rationaleMsgId: Int, private vararg val allRequiredPermissions: String) :
        PermissionRequestStrategy {
    override fun requestPermissions() {
        when {
            PermissionUtil.permissionApi.anyPermissionPermanentlyDenied(activity, * allRequiredPermissions) ->
                PermissionUtil.suggestUserEnablePermsInSettings(activity, rationaleMsgId)

            else -> PermissionUtil.permissionApi.requestPermissions(activity, permissionRequestCode,
                    * allRequiredPermissions)
        }
    }
}
