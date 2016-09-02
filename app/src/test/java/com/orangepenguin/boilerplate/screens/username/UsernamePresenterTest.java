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

import rx.Scheduler;
import rx.schedulers.Schedulers;
import rx.schedulers.TestScheduler;
import rx.subjects.PublishSubject;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
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
    private TestScheduler scheduler = Schedulers.test(); // observeScheduler to advance time by hand

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

    @Test
    public void shouldUpdateLoadingIndicatorOnSetView() {
        // arrange
        setUpDelayedUsersResponse();
        usernamePresenter.setView(mockView);
        // act
        usernamePresenter.showUserButtonPressed(USERNAME, false);
        // assert
        verify(mockView).showLoadingIndicator();
        // arrange
        UsernameContract.View newMockView = mock(UsernameContract.View.class);
        // Act
        usernamePresenter.setView(newMockView);
        // Assert
        verify(newMockView).showLoadingIndicator();
        // Act
        scheduler.advanceTimeBy(1500, MILLISECONDS); // User object should now have been returned
        // Assert
        verify(mockView, never()).hideLoadingIndicator();
        verify(newMockView, times(1)).hideLoadingIndicator();
        verify(newMockView).startDetailsActivity(mockUser);
    }

    private void setUpDelayedUsersResponse() {
        Scheduler.Worker worker = scheduler.createWorker(); // worker to schedule events in time
        // Subjects allow both input and output, so they can be swapped in for Observable calls to unit test your code.
        final PublishSubject<User> mockUserObservable = PublishSubject.create();
        // schedule an observable event to occur at 1000 ms - return successful response with User object
        worker.schedule(() -> mockUserObservable.onNext(mockUser), 1000, MILLISECONDS);
        // configure mock client to actually use mock Observable
        when(mockGitHubClient.user(USERNAME)).thenReturn(mockUserObservable);
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
                                        .observeOnScheduler(Schedulers.immediate())
                                        .build())
                        .build();
        Injector.setPresenterComponent(testPresenterComponent);
    }
}
