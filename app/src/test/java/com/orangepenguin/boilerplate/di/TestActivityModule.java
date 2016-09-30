package com.orangepenguin.boilerplate.di;

import com.squareup.picasso.Picasso;

import dagger.Module;
import dagger.Provides;

import static org.mockito.Mockito.mock;

@Module
public final class TestActivityModule {
    private Picasso picasso = mock(Picasso.class);

    @Provides
    public Picasso providePicasso() {
        return picasso;
    }
}