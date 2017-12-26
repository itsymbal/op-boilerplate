package com.orangepenguin.boilerplate.screens.userdetails

import android.widget.ImageView
import com.orangepenguin.boilerplate.di.UnitTestApplicationComponent
import com.orangepenguin.boilerplate.di.UnitTestComponentFactory
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class UserDetailsActivityTestJDK {

    private lateinit var userDetailsActivity: UserDetailsActivity
    private lateinit var applicationComponent: UnitTestApplicationComponent

    @Before
    @Throws(Exception::class)
    fun setUp() {
        val unitTestComponentFactory = UnitTestComponentFactory()
        applicationComponent = unitTestComponentFactory.applicationComponent
        userDetailsActivity = UserDetailsActivity()
        applicationComponent.inject(userDetailsActivity)
    }

    @Test
    @Ignore
            // ignoring this test, in anticipation of changing go MVVM pattern where method under test will not be called
    fun loadAvatarUrlShouldCallImageLoader() {
        val imageFetcher = applicationComponent.imageFetcher
        val imageView = mock(ImageView::class.java)
//        userDetailsActivity.ivAvatar = imageView

        userDetailsActivity.setAvatarUrl("test_url")

        verify(imageFetcher).fetchAndSetImage("test_url", imageView)
    }
}
