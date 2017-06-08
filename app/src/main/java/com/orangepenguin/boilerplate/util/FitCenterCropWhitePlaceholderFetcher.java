package com.orangepenguin.boilerplate.util;

import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import static com.orangepenguin.boilerplate.BuildConfig.DEBUG;

/**
 * Fetches and location image and sets it on an {@link ImageView}.
 */
public class FitCenterCropWhitePlaceholderFetcher implements ImageFetcher<String, ImageView> {

    @Inject
    public FitCenterCropWhitePlaceholderFetcher() {
    }

    @Override
    public void fetchAndSetImage(String source, ImageView target) {
        Picasso picasso = Picasso.with(target.getContext());
        if (DEBUG) picasso.setIndicatorsEnabled(true);
        picasso.load(source)
                .placeholder(android.R.color.white)
                .fit()
                .centerCrop()
                .into(target);
    }
}
