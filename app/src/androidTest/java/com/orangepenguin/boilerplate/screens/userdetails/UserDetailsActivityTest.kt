package com.orangepenguin.boilerplate.screens.userdetails

import android.support.test.InstrumentationRegistry
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.widget.ImageView
import com.orangepenguin.boilerplate.Application
import com.orangepenguin.boilerplate.R
import com.orangepenguin.boilerplate.di.AndroidTestComponentFactory
import com.orangepenguin.boilerplate.di.ApplicationComponent
import com.orangepenguin.boilerplate.di.Injector
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito

@RunWith(AndroidJUnit4::class)
class UserDetailsActivityTest {
    @JvmField
    @Rule
    var activityRule = ActivityTestRule(UserDetailsActivity::class.java, false, false)
    lateinit var applicationComponent: ApplicationComponent
    lateinit var userDetailsActivity: UserDetailsActivity
    lateinit var imageView: ImageView

    @Before
    fun setUp() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
                .applicationContext as Application
        Injector.setComponentFactory(AndroidTestComponentFactory(context))
        applicationComponent = Injector.getComponentFactory().applicationComponent

        userDetailsActivity = activityRule.launchActivity(null)

    }

    @Test
    fun loadAvatarUrlShouldCallImageLoader() {
        val imageFetcher = applicationComponent.imageFetcher
        var imageView = userDetailsActivity.findViewById(R.id.avatar_imageview) as ImageView

        userDetailsActivity.setAvatarUrl("test_url")

        Mockito.verify(imageFetcher).fetchAndSetImage("test_url", imageView)
    }
}
