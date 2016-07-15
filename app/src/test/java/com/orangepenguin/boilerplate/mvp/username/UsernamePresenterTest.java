package com.orangepenguin.boilerplate.mvp.username;

import com.orangepenguin.boilerplate.di.Injector;
import com.orangepenguin.boilerplate.di.PresenterComponent;
import com.orangepenguin.boilerplate.di.PresenterScope;
import com.orangepenguin.boilerplate.singletons.Constants;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import dagger.Component;
import dagger.Module;

import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.when;


/**
 * running with MockitoJUnitRunner enables the use of @Mock annotations
 */
@RunWith(MockitoJUnitRunner.class)
public class UsernamePresenterTest {
    private static final String USERNAME = "testUsername";

    @Mock UsernameContract.View mockView;

    private UsernamePresenter usernamePresenter;

    @Before
    public void setUp() {
        usernamePresenter = new UsernamePresenter();
        setUpDependencies();
    }

    @Test
    public void shouldStartListActivityOnShowUserButtonPress() {
        usernamePresenter.setView(mockView);
        usernamePresenter.showUserButtonPressed(USERNAME, false);
        verify(mockView).startDetailsActivity(USERNAME);
    }

    /**
     * this test illustrates saving a preference
     */
    @Test
    public void shouldSetUsernamePreferenceIfCheckboxChecked() {
        usernamePresenter.setView(mockView);
        usernamePresenter.showUserButtonPressed(USERNAME, true);
        verify(mockView).savePreference(Constants.PREF_USERNAME, USERNAME);
    }

    /**
     * this test illustrates clearing a saved preference
     */
    @Test
    public void shouldClearPreferenceIfCheckboxNotChecked() {
        usernamePresenter.setView(mockView);
        usernamePresenter.showUserButtonPressed(USERNAME, false);
        verify(mockView).clearPreference(Constants.PREF_USERNAME);
    }

    /**
     * this illustrates mocking a saved preference
     */
    @Test
    public void shouldPopulateUsernameAndRememberCheckboxIfUsernamePreferenceSet() {
        when(mockView.getPreference(Constants.PREF_USERNAME, null)).thenReturn(USERNAME);

        usernamePresenter.setView(mockView);

        verify(mockView).getPreference(Constants.PREF_USERNAME, null);
        verify(mockView).setUsername(USERNAME);
        verify(mockView).checkRememberCheckbox();
    }

    private void setUpDependencies() {
        TestPresenterComponent testPresenterComponent =
                DaggerUsernamePresenterTest_TestPresenterComponent
                        .builder()
                        .testPresenterModule(new TestPresenterModule())
                        .build();
        Injector.setPresenterComponent(testPresenterComponent);
    }

    @PresenterScope
    @Component(modules = {TestPresenterModule.class})
    public interface TestPresenterComponent extends PresenterComponent {
        void inject(UsernamePresenter usernamePresenter);

    }

    @Module
    static final class TestPresenterModule {
        // Don't need anything injected in this simple presenter
    }
}
