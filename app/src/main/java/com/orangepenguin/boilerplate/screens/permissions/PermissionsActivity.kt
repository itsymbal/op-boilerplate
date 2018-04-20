package com.orangepenguin.boilerplate.screens.permissions

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.os.Bundle
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import com.orangepenguin.boilerplate.BaseActivity
import com.orangepenguin.boilerplate.R
import com.orangepenguin.boilerplate.util.PermissionApi
import com.orangepenguin.boilerplate.util.PermissionUtil
import kotlinx.android.synthetic.main.activity_permissions.*
import javax.inject.Inject

private const val RESULT_PERMS_LOCATION = 1
class PermissionsActivity : BaseActivity<PermissionsPresenter>() {

    @Inject override lateinit var presenter: PermissionsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        viewComponent.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_permissions)

        button_location.setOnClickListener({
            if (PermissionApi.permissionsGranted(this, ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION)) {
                requestLocation()
            } else {
                PermissionUtil.requestPermissionsShowingRationaleIfDeniedOnce(this, RESULT_PERMS_LOCATION,
                        R.string.permission_rationale_location, ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION)
            }
        })
    }

    private fun requestLocation() {
        Toast.makeText(this,"Requesting location ", LENGTH_LONG).show()
    }

    override fun onRequestPermissionsResult (requestCode:Int, perms:Array<String>, grantResults:IntArray) {
        super.onRequestPermissionsResult(requestCode, perms, grantResults)

        PermissionApi.setPermissionRequestedFlag(this, perms)

        if (requestCode == RESULT_PERMS_LOCATION) {
            if (PermissionApi.permissionsGranted(this, ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION)) {
                requestLocation()
            } else {
                // permissions not granted; do nothing.
            }
        }
    }
}
