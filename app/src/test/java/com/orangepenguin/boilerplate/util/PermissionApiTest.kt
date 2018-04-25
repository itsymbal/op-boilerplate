package com.orangepenguin.boilerplate.util

import android.Manifest.permission.*
import android.content.Context
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.support.v4.app.ActivityCompat
import com.orangepenguin.boilerplate.BuildConfig
import com.orangepenguin.boilerplate.BuildConfig.APPLICATION_ID
import com.orangepenguin.boilerplate.TestActivity
import com.orangepenguin.boilerplate.TestApplication
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.powermock.api.mockito.PowerMockito
import org.powermock.core.classloader.annotations.PowerMockIgnore
import org.powermock.core.classloader.annotations.PrepareForTest
import org.powermock.modules.junit4.rule.PowerMockRule
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.powermock.api.mockito.PowerMockito.mock
import org.powermock.api.mockito.PowerMockito.spy
import org.powermock.api.mockito.PowerMockito.`when`


@RunWith(RobolectricTestRunner::class)
@Config(application = TestApplication::class, constants = BuildConfig::class, sdk = [23])
@PowerMockIgnore("org.mockito.*", "org.robolectric.*", "android.*" )
@PrepareForTest(ActivityCompat::class)
class PermissionApiTestRobo {

    private lateinit var context: Context
    private lateinit var activity: TestActivity

    @JvmField
    @Rule
    var rule = PowerMockRule()

    @Before
    fun setUp() {
        activity = Robolectric.setupActivity(TestActivity::class.java)
        PowerMockito.mockStatic(ActivityCompat::class.java)
    }

    @Test
    fun testPermissionGranted() {
        PowerMockito.mockStatic(ActivityCompat::class.java)
        `when`(ActivityCompat.checkSelfPermission(context, ACCESS_FINE_LOCATION)).thenReturn(PERMISSION_GRANTED)
//        grantPermissions(ACCESS_FINE_LOCATION)
        assertTrue(PermissionApi.permissionGranted(context, ACCESS_FINE_LOCATION))
    }

    @Test
    fun testPermissionNotGranted() {
        assertFalse(PermissionApi.permissionGranted(context, READ_CALENDAR))
    }

    @Test
    fun testPermissionsGranted() {
        grantPermissions(ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION)
        assertTrue(PermissionApi.permissionsGranted(context, ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION))
    }

    @Test
    fun testPermissionNotDenied() {
        assertFalse(PermissionApi.permissionDenied(activity, READ_CALENDAR))
    }

    @Test
    fun testPermissionDenied() {
        denyPermissions(ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION)
        assertTrue(PermissionApi.permissionDenied(activity, ACCESS_FINE_LOCATION))
        assertFalse(PermissionApi.permissionDenied(activity, READ_CALENDAR))
    }

    //TODO:
    // 1. Add tests for permanently denied (set property and set permission to denied as preconditions) and not - both must be set
    // 2. Add tests for setting flag (check Preferences)

    private fun grantPermissions(vararg permissions: String) {
        permissions.forEach { runShellCommand("adb shell pm grant $APPLICATION_ID $it") }
    }

    private fun denyPermissions(vararg permissions: String) {
        permissions.forEach { runShellCommand("adb shell pm revoke $APPLICATION_ID $it") }
    }

    // TODO: this command fails on Nexus5 but works on GS3. Convert to Instrumentation based code, like in GrantPermissionRule
    private fun runShellCommand(command: String) {
//        val process = Runtime.getRuntime().exec(command, null, null)
//        process.waitFor()
//        executeShellCommand(command)
//        InstrumentationRegistry.getInstrumentation().uiAutomation.executeShellCommand(command)
    }


}
