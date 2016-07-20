package com.orangepenguin.boilerplate.mvp.userdetails;

import dagger.Subcomponent;

@UserDetailsScope
@Subcomponent(modules = {UserDetailsModule.class})
public interface UserDetailsComponent {
    void inject(UserDetailsActivity activity);
}
