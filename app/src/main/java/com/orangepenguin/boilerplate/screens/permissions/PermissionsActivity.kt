package com.orangepenguin.boilerplate.screens.permissions

import android.os.Bundle
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import com.orangepenguin.boilerplate.BaseActivity
import com.orangepenguin.boilerplate.R
import com.orangepenguin.boilerplate.util.PermissionApi
import com.orangepenguin.boilerplate.util.PermissionUtil
import kotlinx.android.synthetic.main.activity_permissions.*
import javax.inject.Inject

class PermissionsActivity : BaseActivity<PermissionsPresenter>() {
    @Inject override lateinit var presenter: PermissionsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        viewComponent.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_permissions)

        button_location.setOnClickListener({
            if (PermissionApi.permissionsGranted(this, PermissionUtil.PERMS_LOCATION)) {
                requestLocation()
            } else {
                PermissionUtil.requestPermissionsShowingRationaleIfDeniedOnce(this,
                        PermissionUtil.RESULT_PERMS_LOCATION,
                        R.string.permission_rationale_location, PermissionUtil.PERMS_LOCATION)
            }
        })
    }

    private fun requestLocation() {
        Toast.makeText(this,"Requesting location ", LENGTH_LONG).show()
    }

    override fun onRequestPermissionsResult (requestCode:Int, perms:Array<String>, grantResults:IntArray) {
        super.onRequestPermissionsResult(requestCode, perms, grantResults)

        PermissionApi.setPermissionRequestedFlag(this, perms)

        if (requestCode == PermissionUtil.RESULT_PERMS_LOCATION) {
            if (PermissionApi.permissionsGranted(this, PermissionUtil.PERMS_LOCATION)) {
                requestLocation()
            } else {
                // permissions not granted; do nothing.
            }
        }
    }
}