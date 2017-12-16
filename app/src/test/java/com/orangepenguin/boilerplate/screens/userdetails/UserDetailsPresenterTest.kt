package com.orangepenguin.boilerplate.screens.userdetails

import com.orangepenguin.boilerplate.model.User
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.runners.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class UserDetailsPresenterTest {

    @Mock lateinit var mockView: UserDetailsView
    @Mock lateinit var mockUser: User
    private lateinit var userDetailsPresenter: UserDetailsPresenter

    @Before
    fun setUp() {
        userDetailsPresenter = UserDetailsPresenter()
        userDetailsPresenter.takeView(mockView)
    }

    @Test
    fun testSavingState() {
        userDetailsPresenter.user = mockUser
        val state = userDetailsPresenter.onSaveInstanceState()
        assertEquals(mockUser, state)
    }
}
