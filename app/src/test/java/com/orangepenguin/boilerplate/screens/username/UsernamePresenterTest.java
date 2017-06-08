package com.orangepenguin.boilerplate.screens.username;

import com.orangepenguin.boilerplate.model.User;
import com.orangepenguin.boilerplate.singletons.Constants;
import com.orangepenguin.boilerplate.usecase.UserUseCase;
import com.orangepenguin.boilerplate.util.NotificationUtil;
import com.orangepenguin.boilerplate.util.SharedPreferencesUtil;

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
    private final TestScheduler scheduler = Schedulers.test(); // observeScheduler to advance time by hand
    @Mock UsernameView mockView;
    @Mock User mockUser;
    @Mock UserUseCase userUseCase;
    @Mock SharedPreferencesUtil sharedPreferencesUtil;
    @Mock NotificationUtil notificationUtil;
    private UsernamePresenter usernamePresenter;

    @Before
    public void setUp() {
        usernamePresenter = new UsernamePresenter(userUseCase, sharedPreferencesUtil, notificationUtil);
        when(userUseCase.fetchUser(USERNAME)).thenReturn(just(mockUser));
    }

    @Test
    public void shouldStartListActivityOnShowUserButtonPress() {
        usernamePresenter.takeView(mockView);
        usernamePresenter.showUserButtonPressed(USERNAME, false);

        verify(mockView).showLoadingIndicator();
        verify(mockView).hideLoadingIndicator();
        verify(mockView).startDetailsActivity(mockUser);
    }

    /**
     * this test illustrates saving a preference
     */
    @Test
    public void shouldSetUsernamePreferenceIfCheckboxChecked() {
        usernamePresenter.takeView(mockView);
        usernamePresenter.showUserButtonPressed(USERNAME, true);
        verify(sharedPreferencesUtil).savePreference(Constants.PREF_USERNAME, USERNAME);
    }

    /**
     * this test illustrates clearing a saved preference
     */
    @Test
    public void shouldClearPreferenceIfCheckboxNotChecked() {
        usernamePresenter.takeView(mockView);
        usernamePresenter.showUserButtonPressed(USERNAME, false);
        verify(sharedPreferencesUtil).clearPreference(Constants.PREF_USERNAME);
    }

    /**
     * this illustrates mocking a saved preference
     */
    @Test
    public void shouldPopulateUsernameAndRememberCheckboxIfUsernamePreferenceSet() {
        when(sharedPreferencesUtil.getPreference(Constants.PREF_USERNAME, null)).thenReturn
                (USERNAME);

        usernamePresenter.takeView(mockView);

        verify(sharedPreferencesUtil).getPreference(Constants.PREF_USERNAME, null);
        verify(mockView).setUsername(USERNAME);
        verify(mockView).checkRememberCheckbox();
    }

    @Test
    public void shouldUpdateLoadingIndicatorOntakeView() {
        // arrange
        setUpDelayedUsersResponse();
        usernamePresenter.takeView(mockView);
        // act
        usernamePresenter.showUserButtonPressed(USERNAME, false);
        // assert
        verify(mockView).showLoadingIndicator();
        // arrange
        UsernameView newMockView = mock(UsernameView.class);
        // Act
        usernamePresenter.takeView(newMockView);
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
        PublishSubject<User> mockUserObservable = PublishSubject.create();
        // schedule an observable event to occur at 1000 ms - return successful response with User object
        worker.schedule(() -> mockUserObservable.onNext(mockUser), 1000, MILLISECONDS);
        worker.schedule(() -> mockUserObservable.onCompleted(), 1000, MILLISECONDS);
        // configure mock client to actually use mock Observable
        when(userUseCase.fetchUser(USERNAME)).thenReturn(mockUserObservable);
    }
}
