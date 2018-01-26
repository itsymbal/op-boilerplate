package com.orangepenguin.boilerplate.screens.username

import com.orangepenguin.boilerplate.model.User
import com.orangepenguin.boilerplate.rx.RxTestSchedulers
import com.orangepenguin.boilerplate.singletons.Constants
import com.orangepenguin.boilerplate.usecase.UserUseCase
import com.orangepenguin.boilerplate.util.NotificationUtil
import com.orangepenguin.boilerplate.util.SharedPreferencesUtil
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import rx.Observable
import rx.Observable.just
import rx.schedulers.Schedulers
import rx.subjects.PublishSubject
import java.util.concurrent.TimeUnit.MILLISECONDS


/**
 * running with MockitoJUnitRunner enables the use of @Mock annotations
 */
@RunWith(MockitoJUnitRunner::class)
class UsernamePresenterTest {
    private val scheduler = Schedulers.test() // observeScheduler to advance time by hand
    @Mock    lateinit var mockView: UsernameView
    @Mock    lateinit var mockUser: User
    @Mock    lateinit var userUseCase: UserUseCase
    @Mock    lateinit var sharedPreferencesUtil: SharedPreferencesUtil
    @Mock    lateinit var notificationUtil: NotificationUtil
    private lateinit var usernamePresenter: UsernamePresenter
    private val schedulers = RxTestSchedulers()

    @Before
    fun setUp() {
        `when`<Observable<User>>(userUseCase.fetchUser(USERNAME)).thenReturn(just<User>(mockUser))
        usernamePresenter = UsernamePresenter(userUseCase, sharedPreferencesUtil, notificationUtil,
                schedulers)
    }

    @Test
    fun shouldStartListActivityOnShowUserButtonPress() {
        usernamePresenter.takeView(mockView)
        usernamePresenter.showUserButtonPressed(USERNAME, false)

        verify<UsernameView>(mockView).showLoadingIndicator()
        verify<UsernameView>(mockView).hideLoadingIndicator()
        verify<UsernameView>(mockView).startDetailsActivity(mockUser)
    }

    /**
     * this test illustrates saving a preference
     */
    @Test
    fun shouldSetUsernamePreferenceIfCheckboxChecked() {
        usernamePresenter.takeView(mockView)
        usernamePresenter.showUserButtonPressed(USERNAME, true)
        verify<SharedPreferencesUtil>(sharedPreferencesUtil).savePreference(Constants.PREF_USERNAME,
                USERNAME)
    }

    /**
     * this test illustrates clearing a saved preference
     */
    @Test
    fun shouldClearPreferenceIfCheckboxNotChecked() {
        usernamePresenter.takeView(mockView)
        usernamePresenter.showUserButtonPressed(USERNAME, false)
        verify<SharedPreferencesUtil>(sharedPreferencesUtil).clearPreference(
                Constants.PREF_USERNAME)
    }

    /**
     * this illustrates mocking a saved preference
     */
    @Test
    @Ignore //TODO: get this mocking nullable business working
    fun shouldPopulateUsernameAndRememberCheckboxIfUsernamePreferenceSet() {
        `when`(sharedPreferencesUtil.getPreference(eq(Constants.PREF_USERNAME),
                ArgumentMatchers.isNull<String>())).thenReturn(USERNAME)

        usernamePresenter.takeView(mockView)

        verify<SharedPreferencesUtil>(sharedPreferencesUtil).getPreference(Constants.PREF_USERNAME,
                ArgumentMatchers.isNull<String>())
        verify<UsernameView>(mockView).setUsername(USERNAME)
        verify<UsernameView>(mockView).checkRememberCheckbox()
    }

    @Test
    fun shouldUpdateLoadingIndicatorOntakeView() {
        // arrange
        setUpDelayedUsersResponse()
        usernamePresenter.takeView(mockView)
        // act
        usernamePresenter.showUserButtonPressed(USERNAME, false)
        // assert
        verify<UsernameView>(mockView).showLoadingIndicator()
        // arrange
        val newMockView = mock(UsernameView::class.java)
        // Act
        usernamePresenter.takeView(newMockView)
        // Assert
        verify(newMockView).showLoadingIndicator()
        // Act
        scheduler.advanceTimeBy(1500, MILLISECONDS) // User object should now have been returned
        // Assert
        verify<UsernameView>(mockView, never()).hideLoadingIndicator()
        verify(newMockView, times(1)).hideLoadingIndicator()
        verify(newMockView).startDetailsActivity(mockUser)
    }

    private fun setUpDelayedUsersResponse() {
        val worker = scheduler.createWorker() // worker to schedule events in time
        // Subjects allow both input and output, so they can be swapped in for Observable calls to unit test your code.
        val mockUserObservable = PublishSubject.create<User>()
        // schedule an observable event to occur at 1000 ms - return successful response with User object
        worker.schedule({ mockUserObservable.onNext(mockUser) }, 1000, MILLISECONDS)
        worker.schedule({ mockUserObservable.onCompleted() }, 1000, MILLISECONDS)
        // configure mock client to actually use mock Observable
        `when`<Observable<User>>(userUseCase.fetchUser(USERNAME)).thenReturn(mockUserObservable)
    }

    companion object {
        private val USERNAME = "testUsername"
    }
}
