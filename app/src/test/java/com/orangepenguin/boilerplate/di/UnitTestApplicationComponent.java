package com.orangepenguin.boilerplate.di;

import android.widget.ImageView;

import com.orangepenguin.boilerplate.screens.userdetails.UserDetailsActivity;
import com.orangepenguin.boilerplate.util.ImageFetcher;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {TestApplicationModule.class})
public interface UnitTestApplicationComponent {
    void inject(UserDetailsActivity activity);
    // add an inject line here for every View and other object that has to be field-injected

    @Named("fitCenterCropWhitePlaceholder")
    ImageFetcher<String, ImageView> getImageFetcher();
}
