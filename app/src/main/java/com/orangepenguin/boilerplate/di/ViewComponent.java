package com.orangepenguin.boilerplate.di;

import com.orangepenguin.boilerplate.screens.permissions.PermissionsActivity;
import com.orangepenguin.boilerplate.screens.userdetails.UserDetailsActivity;
import com.orangepenguin.boilerplate.screens.username.UsernameActivity;

import dagger.Subcomponent;

/**
 * Inject dependencies (e.g. Presenters) into Views
 */
@Subcomponent(modules = {PresenterModule.class})
@PerScreen
public interface ViewComponent {
    void inject(UsernameActivity usernameActivity);
    void inject(UserDetailsActivity userDetailsActivity);
    void inject(PermissionsActivity permissionsActivity);
}
