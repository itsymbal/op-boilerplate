package com.orangepenguin.boilerplate;

import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

@RunWith(RobolectricGradleTestRunner.class)
@Config(packageName = "com.orangepenguin.boilerplate", // needed because we run .debug in debug builds
        constants = BuildConfig.class,
        sdk = 19)
public abstract class BaseRobolectricTest {
}
