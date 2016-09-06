package com.orangepenguin.boilerplate.screens;

import com.orangepenguin.boilerplate.BaseApplication;
import com.orangepenguin.boilerplate.BasePresenter;
import com.orangepenguin.boilerplate.BaseViewInterface;
import com.orangepenguin.boilerplate.di.DaggerTestPresenterComponent;
import com.orangepenguin.boilerplate.di.Injector;
import com.orangepenguin.boilerplate.di.TestPresenterComponent;
import com.orangepenguin.boilerplate.di.TestPresenterModule;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import rx.Subscription;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class BasePresenterTest {
    @Mock
    BaseViewInterface mockView;
    private TestPresenter testPresenter;

    @Before
    public void setUp() throws Exception {
        setUpDependencies();
        testPresenter = new TestPresenter();
    }

    @Test
    public void onDestroyShouldUnsubscribeIfNotAlreadyUnsubscribed() {
        Subscription mockSubscription = mock(Subscription.class);
        when(mockSubscription.isUnsubscribed()).thenReturn(false);
        testPresenter.subscription = mockSubscription;

        testPresenter.onDestroy();

        verify(mockSubscription, times(1)).unsubscribe();
    }

    @Test
    public void onDestroyShouldNotUnsubscribeIfAlreadyUnsubscribed() {
        Subscription mockSubscription = mock(Subscription.class);
        when(mockSubscription.isUnsubscribed()).thenReturn(true);
        testPresenter.subscription = mockSubscription;

        testPresenter.onDestroy();

        verify(mockSubscription, never()).unsubscribe();
    }

    private void setUpDependencies() {
        TestPresenterComponent testPresenterComponent =
                DaggerTestPresenterComponent
                        .builder()
                        .testPresenterModule(
                                TestPresenterModule
                                        .builder()
                                        .baseApplication(mock(BaseApplication.class))
                                        .build())
                        .build();
        Injector.setPresenterComponent(testPresenterComponent);
    }


    class TestPresenter extends BasePresenter {
        @Override
        public void setView(BaseViewInterface view) {
            super.setView(view);
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
        }
    }
}