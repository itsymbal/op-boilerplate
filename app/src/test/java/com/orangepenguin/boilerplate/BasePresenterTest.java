package com.orangepenguin.boilerplate;

import com.orangepenguin.boilerplate.di.Injector;
import com.orangepenguin.boilerplate.di.UnitTestComponentFactory;

import org.junit.Before;
import org.junit.Test;

import rx.Subscription;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class BasePresenterTest {
    private TestPresenter testPresenter;

    @Before
    public void setUp() throws Exception {
        Injector.setComponentFactory(new UnitTestComponentFactory());

        testPresenter = new TestPresenter();
    }

    @Test
    public void onDestroyShouldUnsubscribeIfNotAlreadyUnsubscribed() {
        Subscription mockSubscription = mock(Subscription.class);
        when(mockSubscription.isUnsubscribed()).thenReturn(false);
        testPresenter.addDisposableSubscription(mockSubscription);

        testPresenter.terminate();

        verify(mockSubscription, times(1)).unsubscribe();
    }

    @Test
    public void onDestroyShouldNotUnsubscribeIfAlreadyUnsubscribed() {
        Subscription mockSubscription = mock(Subscription.class);
        when(mockSubscription.isUnsubscribed()).thenReturn(true);
        testPresenter.addDisposableSubscription(mockSubscription);

        testPresenter.terminate();

        verify(mockSubscription, never()).unsubscribe();
    }


    class TestPresenter extends BasePresenter<TestView> {
    }

    class TestView extends BaseActivity<TestPresenter> {
    }
}
