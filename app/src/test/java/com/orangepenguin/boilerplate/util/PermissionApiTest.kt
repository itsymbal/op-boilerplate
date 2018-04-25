package com.orangepenguin.boilerplate.util

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.Manifest.permission.READ_CALENDAR
import android.Manifest.permission.READ_CONTACTS
import android.Manifest.permission.READ_SMS
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager.PERMISSION_DENIED
import android.content.pm.PackageManager.PERMISSION_GRANTED
import com.orangepenguin.boilerplate.TestActivity
import com.orangepenguin.boilerplate.util.PermissionApi.SystemApiInterface
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.reset
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class PermissionApiTest {

    @Mock lateinit var context: Context
    @Mock lateinit var activity: TestActivity
    @Mock lateinit var mockSystemInterface: SystemApiInterface
    private var permissionApi = PermissionApi

    @Before
    fun setUp() {
        permissionApi.systemApiInterface = mockSystemInterface
        reset(mockSystemInterface)
        noPermissionsGranted()
    }

    @Test
    fun testNoPermissionsGranted() {
        assertFalse(permissionApi.permissionGranted(context, READ_CALENDAR))
        assertFalse(permissionApi.permissionsGranted(context, ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION))
        assertFalse(permissionApi.permissionDenied(activity, READ_CALENDAR))
        assertFalse(permissionApi.anyPermissionDenied(activity, READ_CALENDAR, READ_SMS))
        assertFalse(permissionApi.permissionPermanentlyDenied(activity, ACCESS_FINE_LOCATION))
        assertFalse(permissionApi.anyPermissionPermanentlyDenied(activity, READ_CALENDAR, READ_SMS))
    }

    @Test
    fun testGrantedPermissions() {
        grantPermissions(ACCESS_FINE_LOCATION, READ_SMS)

        assertTrue(permissionApi.permissionGranted(context, READ_SMS))
        assertTrue(permissionApi.permissionsGranted(context, ACCESS_FINE_LOCATION, READ_SMS))
        assertFalse(permissionApi.permissionGranted(context, READ_CONTACTS))
        assertFalse(permissionApi.permissionDenied(activity, READ_CALENDAR))
        assertFalse(permissionApi.anyPermissionDenied(activity, READ_CALENDAR, READ_SMS))
        assertFalse(permissionApi.permissionPermanentlyDenied(activity, ACCESS_FINE_LOCATION))
        assertFalse(permissionApi.anyPermissionPermanentlyDenied(activity, READ_CALENDAR, READ_SMS))
    }

    @Test
    fun testDeniedPermission() {
        denyPermissions(activity, ACCESS_FINE_LOCATION, READ_SMS)

        assertFalse(permissionApi.permissionGranted(context, READ_SMS))
        assertFalse(permissionApi.permissionsGranted(context, ACCESS_FINE_LOCATION, READ_SMS))
        assertTrue(permissionApi.anyPermissionDenied(activity, READ_CALENDAR, READ_SMS))
        assertTrue(permissionApi.permissionDenied(activity, ACCESS_FINE_LOCATION))
        assertFalse(permissionApi.permissionDenied(activity, READ_CALENDAR))
        assertFalse(permissionApi.permissionPermanentlyDenied(activity, ACCESS_FINE_LOCATION))
        assertFalse(permissionApi.anyPermissionPermanentlyDenied(activity, READ_CALENDAR, READ_SMS))
    }

    @Test
    fun testPermanentlyDeniedPermissions() {
        permanentlyDenyPermissions(activity, ACCESS_FINE_LOCATION, READ_SMS)

        assertFalse(permissionApi.permissionGranted(context, READ_SMS))
        assertFalse(permissionApi.permissionsGranted(context, ACCESS_FINE_LOCATION, READ_SMS))
        assertFalse(permissionApi.anyPermissionDenied(activity, READ_CALENDAR, READ_SMS))
        assertFalse(permissionApi.permissionDenied(activity, ACCESS_FINE_LOCATION))
        assertFalse(permissionApi.permissionPermanentlyDenied(activity, READ_CALENDAR))
        assertTrue(permissionApi.permissionPermanentlyDenied(activity, ACCESS_FINE_LOCATION))
        assertTrue(permissionApi.anyPermissionPermanentlyDenied(activity, READ_CALENDAR, READ_SMS))
    }

    // TODO
    // 2. Add tests for setting flag (check Preferences)

    private fun noPermissionsGranted() {
        upon(mockSystemInterface.checkSelfPermission(eq(context), any())).thenReturn(PERMISSION_DENIED)
    }

    private fun grantPermissions(vararg permissions: String) {
        permissions.forEach { upon(mockSystemInterface.checkSelfPermission(context, it)).thenReturn(PERMISSION_GRANTED) }
    }

    private fun denyPermissions(activity: Activity, vararg permissions: String) {
        permissions.forEach { upon(mockSystemInterface.shouldShowRequestPermissionRationale(activity, it)).thenReturn(true) }
    }

    private fun permanentlyDenyPermissions(activity: Activity, vararg permissions: String) {
        permissions.forEach {
            upon(mockSystemInterface.checkSelfPermission(activity, it)).thenReturn(PERMISSION_DENIED)
            upon(mockSystemInterface.shouldShowRequestPermissionRationale(activity, it)).thenReturn(false)
            upon(mockSystemInterface.getPreference(activity, "PREF_HAS_REQUESTED_PERM_$it", false)).thenReturn(true)
        }
    }
 }
