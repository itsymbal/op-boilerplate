package com.orangepenguin.boilerplate.screens.userdetails;

import android.widget.ImageView;

import com.orangepenguin.boilerplate.BaseRobolectricTest;
import com.orangepenguin.boilerplate.di.DaggerTestActivityComponent;
import com.orangepenguin.boilerplate.di.Injector;
import com.orangepenguin.boilerplate.di.TestActivityComponent;
import com.orangepenguin.boilerplate.di.TestActivityModule;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;

import static com.orangepenguin.boilerplate.fixtures.ActivityFixtures.buildAndStartActivity;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserDetailsActivityTest extends BaseRobolectricTest {

    private static Picasso mockPicasso = mock(Picasso.class);
    private static RequestCreator mockRequestCreator = mock(RequestCreator.class);
    private UserDetailsActivity userDetailsActivity;
    private UserDetailsContract.Presenter mockPresenter = mock(UserDetailsContract.Presenter.class);

    @Before
    public void setUp() throws Exception {
        setupTestDependencies();
        userDetailsActivity = buildAndStartActivity(UserDetailsActivity.class);
    }

    @Test
    public void loadAvatarUrlShouldCallPicasso() {
        when(mockPicasso.load("test_url")).thenReturn(mockRequestCreator);

        userDetailsActivity.setAvatarUrl("test_url");

        verify(mockPicasso).load("test_url");
        verify(mockRequestCreator).into((ImageView) Matchers.any());
    }

    /**
     * this setup method is an example to follow. Set up a Test Component, which will be used to inject SUT, and set
     * it on Injector static class. If set, it will be used; if not, a brand new one will be created by Injector
     */
    private void setupTestDependencies() {
        Injector.setPresenter(UserDetailsContract.Presenter.class, mockPresenter);

        TestActivityComponent testSimpleActivityComponent =
                DaggerTestActivityComponent
                        .builder()
                        .testActivityModule(new TestActivityModule())
                        .build();

        Injector.setActivityComponent(testSimpleActivityComponent);
    }
}