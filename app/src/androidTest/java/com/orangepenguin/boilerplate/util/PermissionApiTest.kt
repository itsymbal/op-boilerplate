package com.orangepenguin.boilerplate.util

import android.Manifest.permission.*
import android.app.Activity
import android.content.Context
import android.support.test.InstrumentationRegistry
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.orangepenguin.boilerplate.BuildConfig.APPLICATION_ID
import com.orangepenguin.boilerplate.TestActivity
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PermissionApiTest {

    @JvmField
    @Rule
    var activityRule = ActivityTestRule(TestActivity::class.java, false, true)

//    @Rule @JvmField
//    val grantPermissionRule: GrantPermissionRule = GrantPermissionRule.grant(android.Manifest.permission.ACCESS_FINE_LOCATION)


    private lateinit var context: Context
    private lateinit var activity: Activity

    @Before
    @Throws(Exception::class)
    fun setUp() {
        context = InstrumentationRegistry.getInstrumentation().targetContext
        activity = activityRule.activity

        runShellCommand("adb shell pm reset-permissions $APPLICATION_ID")
    }

    @Test
    fun testPermissionGranted() {
        grantPermissions(ACCESS_FINE_LOCATION)
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

    // TODO: this command fails on Nexus5 but works on GS3. Conver to Instrumentation based code, like in GrantPermissionRule
    private fun runShellCommand(command: String) {
        val process = Runtime.getRuntime().exec(command, null, null)
        process.waitFor()
    }
}
