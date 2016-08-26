package com.orangepenguin.boilerplate.screens.username;

import com.orangepenguin.boilerplate.BaseRobolectricTest;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import dagger.Component;
import dagger.Module;
import dagger.Provides;

import static com.orangepenguin.boilerplate.fixtures.ActivityFixtures.buildAndStartActivity;

public class UsernameActivityTest extends BaseRobolectricTest {

    private UsernameActivity usernameActivity;
    private UsernameContract.Presenter mockPresenter = Mockito.mock(UsernameContract.Presenter.class);

    @Before
    public void setUp() throws Exception {
        setupTestDependencies();
        usernameActivity = buildAndStartActivity(UsernameActivity.class);
    }

    @Test
    public void tstGoHere() {


    }

    /**
     * this setup method is an example to follow. Set up a Test Component, which will be used to inject SUT, and set
     * it on Injector static class. If set, it will be used; if not, a brand new one will be created
     */
    private void setupTestDependencies() {

        TestUsernameComponent testSimpleActivityComponent = DaggerUsernameActivityTest_TestUsernameComponent
                .builder()
                .testSimpleActivityModule(new TestSimpleActivityModule(mockPresenter))
                .build();

        UsernameInjector.setUsernameComponent(testSimpleActivityComponent);
    }

    @UsernameScope
    @Component(modules = {TestSimpleActivityModule.class})
    interface TestUsernameComponent extends UsernameInjector.UsernameComponent {
        void inject(UsernameActivity activity);
    }

    @Module
    static final class TestSimpleActivityModule {
        UsernameContract.Presenter presenter;

        TestSimpleActivityModule(UsernameContract.Presenter presenter) {
            this.presenter = presenter;
        }

        @Provides
        UsernameContract.Presenter providePresenter() {
            return presenter;
        }
    }
}
