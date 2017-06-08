package com.orangepenguin.boilerplate.di;

import android.support.annotation.NonNull;

public interface ComponentFactory {

    @NonNull
    ViewComponent getViewComponent();
}
