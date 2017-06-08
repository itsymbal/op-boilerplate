package com.orangepenguin.boilerplate.di;

import android.widget.ImageView;

import com.orangepenguin.boilerplate.util.ImageFetcher;

import javax.inject.Named;

import dagger.Subcomponent;

@Subcomponent(modules = {TestPresenterModule.class})
@PerScreen
public interface TestViewComponent extends ViewComponent {
    @Named("fitCenterCropWhitePlaceholder")
    ImageFetcher<String, ImageView> getImageFetcher();
}
