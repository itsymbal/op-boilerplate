package com.orangepenguin.boilerplate.util

import android.Manifest.permission.*
import android.app.Activity
import android.app.UiAutomation
import android.content.Context
import android.os.ParcelFileDescriptor
import android.support.test.InstrumentationRegistry
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.util.Log
import com.orangepenguin.boilerplate.BuildConfig.APPLICATION_ID
import com.orangepenguin.boilerplate.TestActivity
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeUnit.SECONDS
import java.util.concurrent.TimeoutException

/**
 * Android Espresso test for permissions. Keeping it here for posterity and for examples of using Espresso syntax.
 * Unfortunately, this could not be made to work. Permissions could not be reliably denied on Android via either adb or
 * instrumentation, without killing the tests. Also there is no way using adb to set permissions to be
 * "denied with Do not ask again"
 * Going to @Ignore actual tests
 * adding 'Espresso' to class name so it does not conflict with jUnit test
 */
@RunWith(AndroidJUnit4::class)
class PermissionApiTestEspresso {

    private val TAG = "PermissionApiTest"

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

        // commenting this out because it kills the app
//        runShellCommand("adb shell pm reset-permissions $APPLICATION_ID")
    }

    @Ignore
    @Test
    fun testPermissionGranted() {
        grantPermissions(ACCESS_FINE_LOCATION)
        assertTrue(PermissionApi.permissionGranted(context, ACCESS_FINE_LOCATION))
    }

    @Ignore
    @Test
    fun testPermissionNotGranted() {
        assertFalse(PermissionApi.permissionGranted(context, READ_CALENDAR))
    }

    @Ignore
    @Test
    fun testPermissionsGranted() {
        grantPermissions(ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION)
        assertTrue(PermissionApi.permissionsGranted(context, ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION))
    }

    @Ignore
    @Test
    fun testPermissionNotDenied() {
        assertFalse(PermissionApi.permissionDenied(activity, READ_CALENDAR))
    }

    @Ignore
    @Test
    fun testPermissionDenied() {
        denyPermissions(ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION)
        assertFalse(PermissionApi.permissionDenied(activity, READ_CALENDAR))
    }

    //TODO:
    // 2. Add tests for setting flag (check Preferences)

    private fun grantPermissions(vararg permissions: String) {
        permissions.forEach { runShellCommand("adb shell pm grant $APPLICATION_ID $it") }
    }

    private fun denyPermissions(vararg permissions: String) {
        permissions.forEach { runShellCommand("adb shell pm revoke $APPLICATION_ID $it") }
    }

    // TODO: this command fails on Nexus5 but works on GS3. Convert to Instrumentation based code, like in GrantPermissionRule
    private fun runShellCommand(command: String) {
// this command works on GS3 but fails on Nexus5 with error
// java.io.IOException: Error running exec(). Command: [adb, shell, pm, reset-permissions, com.orangepenguin.boilerplate.debug] Working Directory: null Environment: nul
        // and on emulator with error
// and on emulator (android 7.1.1, API 25)
        //java.io.IOException: Cannot run program "adb": error=13, Permission denied
        val process = Runtime.getRuntime().exec(command, null, null)
        process.waitFor()
        executeShellCommand(command)
//      The following line would work to deny permissions, but causes an error
// Test failed to run to completion. Reason: 'Test run failed to complete. Expected 5 tests, received 0'. Check device logcat for details
//        this is because denying permissions causes Android to kill and restart the app (and terminate all tests :-(
//        InstrumentationRegistry.getInstrumentation().uiAutomation.executeShellCommand(command)
    }

    /**
     * Shamelessly copied from [android.support.test.runner.permission.UiAutomationShellCommand]
     * Since [UiAutomation.executeShellCommand] does not block until the command
     * executed successfully. Write the output to byte array to make sure permission is granted before
     * starting the test.
     *
     * @param command to run to request permissions
     */
    private fun executeShellCommand(command: String) {
        Log.i(TAG, "Requesting permission: $command")
        try {
            awaitTermination(InstrumentationRegistry.getInstrumentation().uiAutomation.executeShellCommand(command), 2, SECONDS)
        } catch (e: TimeoutException) {
            Log.e(TAG, "Timeout while executing cmd: $command")
        }
    }


    /**
     * Shamelessly copied from [android.support.test.runner.permission.UiAutomationShellCommand]
     * Blocks until the command finished executing.
     *
     * The reason why we need to have this method is
     * because of the way that [UiAutomation.executeShellCommand] is currently
     * implemented in the platform. Ideally executeShellCommand() would just return the exit code to
     * us or give us a callback.
     *
     * This is a better way than just using Thread.sleep(), since it
     * would be really hard to determine what a timeout would be. We would wait too long on a fast
     * device and it might still not work on slow devices.
     *
     * But this solution is not a great way
     * either to determine if a command has finished execution and there might still be situations
     * where even this solution might be flaky. To have another safety net we wait an additional
     * 1000ms in RequestPermissionCallable if the permission was not granted as expected. This seems
     * to be the best we can do for now.
     *
     * @param pfDescriptor Used to read the content returned by shell command
     */
    private fun awaitTermination(pfDescriptor: ParcelFileDescriptor, timeout: Long, unit: TimeUnit) {
        val timeoutInMillis = unit.toMillis(timeout)
        val endTimeInMillis = if (timeoutInMillis > 0) System.currentTimeMillis() + timeoutInMillis else 0
        var reader: BufferedReader? = null
        try {
            reader = BufferedReader(InputStreamReader(ParcelFileDescriptor.AutoCloseInputStream(pfDescriptor)))
            var line: String? = reader.readLine()
            while (line != null) {
                Log.i(TAG, line)
                if (endTimeInMillis > System.currentTimeMillis()) {
                    throw TimeoutException()
                }
                line = reader.readLine()
            }
        } finally {
            if (reader != null) {
                reader.close()
            }
        }
    }

}
