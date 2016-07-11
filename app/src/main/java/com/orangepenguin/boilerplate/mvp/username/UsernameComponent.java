package com.orangepenguin.boilerplate.mvp.username;

import dagger.Subcomponent;

@UsernameScope
@Subcomponent(modules = {UsernameModule.class})
public interface UsernameComponent {
    void inject(UsernameActivity activity);
}
