package com.orangepenguin.boilerplate.screens.username;

import com.orangepenguin.boilerplate.ApplicationInterface;
import com.orangepenguin.boilerplate.di.DaggerTestPresenterComponent;
import com.orangepenguin.boilerplate.di.Injector;
import com.orangepenguin.boilerplate.di.TestPresenterComponent;
import com.orangepenguin.boilerplate.di.TestPresenterModule;
import com.orangepenguin.boilerplate.model.User;
import com.orangepenguin.boilerplate.rest.GitHubClient;
import com.orangepenguin.boilerplate.singletons.Constants;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.when;
import static rx.Observable.just;


/**
 * running with MockitoJUnitRunner enables the use of @Mock annotations
 */
@RunWith(MockitoJUnitRunner.class)
public class UsernamePresenterTest {
    private static final String USERNAME = "testUsername";

    @Mock UsernameContract.View mockView;
    @Mock ApplicationInterface mockApplication;
    @Mock GitHubClient mockGitHubClient;

    @Mock User mockUser;
    private UsernamePresenter usernamePresenter;

    @Before
    public void setUp() {
        setUpDependencies();
        usernamePresenter = new UsernamePresenter();
        when(mockGitHubClient.user(USERNAME)).thenReturn(just(mockUser));
    }

    @Test
    public void shouldStartListActivityOnShowUserButtonPress() {
        usernamePresenter.setView(mockView);
        usernamePresenter.showUserButtonPressed(USERNAME, false);

        verify(mockView).startDetailsActivity(mockUser);
    }

    /**
     * this test illustrates saving a preference
     */
    @Test
    public void shouldSetUsernamePreferenceIfCheckboxChecked() {
        usernamePresenter.setView(mockView);
        usernamePresenter.showUserButtonPressed(USERNAME, true);
        verify(mockApplication).savePreference(Constants.PREF_USERNAME, USERNAME);
    }

    /**
     * this test illustrates clearing a saved preference
     */
    @Test
    public void shouldClearPreferenceIfCheckboxNotChecked() {
        usernamePresenter.setView(mockView);
        usernamePresenter.showUserButtonPressed(USERNAME, false);
        verify(mockApplication).clearPreference(Constants.PREF_USERNAME);
    }

    /**
     * this illustrates mocking a saved preference
     */
    @Test
    public void shouldPopulateUsernameAndRememberCheckboxIfUsernamePreferenceSet() {
        when(mockApplication.getPreference(Constants.PREF_USERNAME, null)).thenReturn(USERNAME);

        usernamePresenter.setView(mockView);

        verify(mockApplication).getPreference(Constants.PREF_USERNAME, null);
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
                                        .baseApplication(mockApplication)
                                        .gitHubClient(mockGitHubClient)
                                        .build())
                        .build();
        Injector.setPresenterComponent(testPresenterComponent);
    }
}
