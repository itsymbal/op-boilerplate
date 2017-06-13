package com.orangepenguin.boilerplate.screens.userdetails;

import android.widget.ImageView;

import com.orangepenguin.boilerplate.di.UnitTestApplicationComponent;
import com.orangepenguin.boilerplate.di.UnitTestComponentFactory;
import com.orangepenguin.boilerplate.util.ImageFetcher;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class UserDetailsActivityTest {

    private UserDetailsActivity userDetailsActivity;
    private UnitTestApplicationComponent applicationComponent;

    @Before
    public void setUp() throws Exception {
        UnitTestComponentFactory unitTestComponentFactory = new UnitTestComponentFactory();
        applicationComponent = unitTestComponentFactory.getApplicationComponent();
        userDetailsActivity = new UserDetailsActivity();
        applicationComponent.inject(userDetailsActivity);
    }

    @Test
    public void loadAvatarUrlShouldCallImageLoader() {
        ImageFetcher<String, ImageView> imageFetcher = applicationComponent.getImageFetcher();
        ImageView imageView = mock(ImageView.class);
        userDetailsActivity.avatarImageView = imageView;

        userDetailsActivity.setAvatarUrl("test_url");

        verify(imageFetcher).fetchAndSetImage("test_url", imageView);
    }
}
