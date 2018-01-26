package com.orangepenguin.boilerplate.util

import android.content.pm.PackageManager
import android.test.mock.MockContext
import android.test.mock.MockPackageManager

import org.junit.Test

import android.content.pm.PackageManager.PERMISSION_DENIED
import android.content.pm.PackageManager.PERMISSION_GRANTED
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue

/**
 * Tests [com.orangepenguin.boilerplate.util.PermissionUtil].
 */
class PermissionUtilTest {

    @Test
    fun testIsPermissionGranted_yes() {
        val context = TestContext(TestPackageManager(PERMISSION_GRANTED))
        assertTrue(PermissionUtil.isPermissionGranted(context, "test"))
    }

    @Test
    fun testIsPermissionGranted_no() {
        val context = TestContext(TestPackageManager(PERMISSION_DENIED))
        assertFalse(PermissionUtil.isPermissionGranted(context, "test"))
    }

    @Test
    fun testArePermissionsGranted_yes() {
        val context = TestContext(TestPackageManager(PERMISSION_GRANTED))
        assertTrue(PermissionUtil.arePermissionsGranted(context, "test"))
    }

    @Test
    fun testArePermissionsGranted_no() {
        val context = TestContext(TestPackageManager(PERMISSION_DENIED))
        assertFalse(PermissionUtil.arePermissionsGranted(context, "test"))
    }

    /**
     * Mock Context to return a fake PackageManager.
     */
    private class TestContext
    /**
     * Constructor.
     *
     * @param testPackageManager the fake package manager to return
     */
    (internal val testPackageManager: TestPackageManager) : MockContext() {

        override fun getPackageManager(): PackageManager {
            return testPackageManager
        }

        override fun getPackageName(): String? {
            return "TestPackage"
        }
    }

    /**
     * Fakes the PackageManager API for getting package data.
     */
    private class TestPackageManager
    /**
     * @param permissionCheckValue the value to return when a permission check occurs.
     */
    internal constructor(private val permissionCheckValue: Int) : MockPackageManager() {

        override fun checkPermission(permissionName: String, packageName: String): Int {
            return permissionCheckValue
        }
    }
}
