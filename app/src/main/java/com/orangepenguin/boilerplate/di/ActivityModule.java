package com.orangepenguin.boilerplate.di;

import android.content.Context;

import com.squareup.picasso.Picasso;

import dagger.Module;
import dagger.Provides;

import static com.orangepenguin.boilerplate.BuildConfig.DEBUG;

@Module
public final class ActivityModule {
    @Provides
    Picasso providePicasso(Context context) {
        Picasso picasso = Picasso.with(context);
        if (DEBUG) picasso.setIndicatorsEnabled(true); //show cached image indicators overlay
        return picasso;
    }
}
