package com.orangepenguin.boilerplate.screens.username

import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.orangepenguin.boilerplate.Application
import com.orangepenguin.boilerplate.R
import com.orangepenguin.boilerplate.di.AndroidTestComponentFactory
import com.orangepenguin.boilerplate.di.Injector
import com.orhanobut.mockwebserverplus.MockWebServerPlus
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UsernameActivityTest {

    @JvmField
    @Rule
    var activityRule = ActivityTestRule(UsernameActivity::class.java, false, false)

    @JvmField
    @Rule
    var server = MockWebServerPlus()

    @Before
    @Throws(Exception::class)
    fun setUp() {

        val context = InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as Application
        Injector.setComponentFactory(AndroidTestComponentFactory(context))
        val serverBaseUrl = server.url("/")
        Injector.getComponentFactory().applicationComponent.gitHubUrl.set(serverBaseUrl)
        activityRule.launchActivity(null)
    }

    @Test
    fun dontDoNothing() {
        server.enqueue("github_user")

        onView(withId(R.id.username_edit_text)).perform(typeText("itsymbal"), closeSoftKeyboard())
        onView(withId(R.id.view_user_button)).perform(click())
        //        onView(withId(R.id.username_textview)).check(isDisplayed());
    }
}
