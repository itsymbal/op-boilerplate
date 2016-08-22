package com.orangepenguin.boilerplate.mvp.username;

import com.orangepenguin.boilerplate.di.DaggerTestPresenterComponent;
import com.orangepenguin.boilerplate.di.Injector;
import com.orangepenguin.boilerplate.di.TestPresenterComponent;
import com.orangepenguin.boilerplate.di.TestPresenterModule;
import com.orangepenguin.boilerplate.singletons.Constants;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.when;


/**
 * running with MockitoJUnitRunner enables the use of @Mock annotations
 */
@RunWith(MockitoJUnitRunner.class)
public class UsernamePresenterTest {
    private static final String USERNAME = "testUsername";

    @Mock
    UsernameContract.View mockView;

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
                DaggerTestPresenterComponent
                        .builder()
                        .testPresenterModule(
                                TestPresenterModule
                                        .builder()
                                        .build())
                        .build();
        Injector.setPresenterComponent(testPresenterComponent);
    }
}
