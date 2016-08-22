package com.orangepenguin.boilerplate.di;

import dagger.Component;

@PresenterScope
@Component(modules = {TestPresenterModule.class})
public interface TestPresenterComponent extends PresenterComponent {
    // this presenter component exists to encapsulate TestPresenterModule, and is used to substitute
    // its parent class PresenterComponent. It does not have any additional methods
}
