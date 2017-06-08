package com.orangepenguin.boilerplate.screens.userdetails;

import android.widget.ImageView;

import com.orangepenguin.boilerplate.di.Injector;
import com.orangepenguin.boilerplate.di.TestViewComponent;
import com.orangepenguin.boilerplate.di.UnitTestComponentFactory;
import com.orangepenguin.boilerplate.util.ImageFetcher;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class UserDetailsActivityTest {

    private UserDetailsActivity userDetailsActivity;
    private TestViewComponent viewComponent;

    @Before
    public void setUp() throws Exception {
        Injector.setComponentFactory(new UnitTestComponentFactory());
        viewComponent = (TestViewComponent) Injector.getComponentFactory().getViewComponent();
        userDetailsActivity = new UserDetailsActivity();
        viewComponent.inject(userDetailsActivity);
    }

    @Test
    public void loadAvatarUrlShouldCallImageLoader() {
        ImageFetcher<String, ImageView> imageFetcher = viewComponent.getImageFetcher();
        ImageView imageView = mock(ImageView.class);
        userDetailsActivity.avatarImageView = imageView;

        userDetailsActivity.setAvatarUrl("test_url");

        verify(imageFetcher).fetchAndSetImage("test_url", imageView);
    }
}
