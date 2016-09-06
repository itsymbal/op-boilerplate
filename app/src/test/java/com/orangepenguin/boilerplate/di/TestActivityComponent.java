package com.orangepenguin.boilerplate.di;

import dagger.Component;

@Component(modules = {TestActivityModule.class})
public interface TestActivityComponent extends ActivityComponent {
}
