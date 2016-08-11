package com.orangepenguin.boilerplate.mvp.username;

import com.orangepenguin.boilerplate.BaseRobolectricTest;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import dagger.Component;
import dagger.Module;
import dagger.Provides;

import static com.orangepenguin.boilerplate.fixtures.ActivityFixtures.buildAndStartActivity;
import static com.orangepenguin.boilerplate.fixtures.ActivityFixtures.emulateConfigurationChange;
import static org.assertj.core.api.Assertions.assertThat;

public class UsernameActivityTest extends BaseRobolectricTest {

    private UsernameActivity usernameActivity;
    private UsernameContract.Presenter mockPresenter = Mockito.mock(UsernameContract.Presenter.class);

    @Before
    public void setUp() throws Exception {
        setupTestDependencies();
        usernameActivity = buildAndStartActivity(UsernameActivity.class);
    }

    /**
     * this test verifies that Presenter survives configuration change. Presenter keeps state of the View, if View has
     * such state. Therefore this test is wicked important. TODO: since functionality moved to Base, consider moving
     * test
     */
    @Test
    public void shouldNotCreateNewPresenterOnConfigurationChange() {
        UsernameContract.Presenter originalPresenter = usernameActivity.presenter;
        usernameActivity = emulateConfigurationChange(usernameActivity);
        assertThat(usernameActivity.presenter).isEqualTo(originalPresenter);
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

    @UsernameScope
    @Component(modules = {TestSimpleActivityModule.class})
    interface TestUsernameComponent extends UsernameInjector.UsernameComponent {
        void inject(UsernameActivity activity);
    }
}
