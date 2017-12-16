package com.orangepenguin.boilerplate

import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*
import rx.Subscription

class BasePresenterTest {
    private lateinit var testPresenter: TestPresenter

    @Before
    @Throws(Exception::class)
    fun setUp() {
        testPresenter = TestPresenter()
    }

    @Test
    fun onDestroyShouldUnsubscribeIfNotAlreadyUnsubscribed() {
        val mockSubscription = mock(Subscription::class.java)
        `when`(mockSubscription.isUnsubscribed).thenReturn(false)
        testPresenter.addDisposableSubscription(mockSubscription)

        testPresenter.terminate()

        verify(mockSubscription, times(1)).unsubscribe()
    }

    @Test
    fun onDestroyShouldNotUnsubscribeIfAlreadyUnsubscribed() {
        val mockSubscription = mock(Subscription::class.java)
        `when`(mockSubscription.isUnsubscribed).thenReturn(true)
        testPresenter.addDisposableSubscription(mockSubscription)

        testPresenter.terminate()

        verify(mockSubscription, never()).unsubscribe()
    }


    internal inner class TestPresenter : BasePresenter()

    internal inner class TestView : BaseActivity<TestPresenter>()
}
