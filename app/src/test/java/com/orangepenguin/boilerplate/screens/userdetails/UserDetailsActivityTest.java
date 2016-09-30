package com.orangepenguin.boilerplate.screens.userdetails;

import android.widget.ImageView;

import com.orangepenguin.boilerplate.BaseRobolectricTest;
import com.orangepenguin.boilerplate.di.ComponentUtil;
import com.orangepenguin.boilerplate.di.Injector;
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

    private static Picasso mockPicasso;
    private static RequestCreator mockRequestCreator = mock(RequestCreator.class);
    private UserDetailsActivity userDetailsActivity;
    private UserDetailsContract.Presenter mockPresenter = mock(UserDetailsContract.Presenter.class);

    @Before
    public void setUp() throws Exception {
        Injector.setPresenter(UserDetailsContract.Presenter.class, mockPresenter);
        mockPicasso = ComponentUtil.setUpTestActivityModule().providePicasso();
        userDetailsActivity = buildAndStartActivity(UserDetailsActivity.class);
    }

    @Test
    public void loadAvatarUrlShouldCallPicasso() {
        when(mockPicasso.load("test_url")).thenReturn(mockRequestCreator);

        userDetailsActivity.setAvatarUrl("test_url");

        verify(mockPicasso).load("test_url");
        verify(mockRequestCreator).into((ImageView) Matchers.any());
    }
}