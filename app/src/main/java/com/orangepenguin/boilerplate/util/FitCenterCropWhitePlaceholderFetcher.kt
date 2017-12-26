package com.orangepenguin.boilerplate.util

import android.widget.ImageView
import com.orangepenguin.boilerplate.BuildConfig.DEBUG
import com.squareup.picasso.Picasso
import javax.inject.Inject

/**
 * Fetches and location image and sets it on an [ImageView].
 */
class FitCenterCropWhitePlaceholderFetcherKt @Inject
constructor() : ImageFetcher<String, ImageView> {

    override fun fetchAndSetImage(source: String, target: ImageView) {
        val picasso = Picasso.with(target.context)
        if (DEBUG) picasso.setIndicatorsEnabled(true)
        picasso.load(source)
                .placeholder(android.R.color.white)
                .fit()
                .centerCrop()
                .into(target)
    }
}
