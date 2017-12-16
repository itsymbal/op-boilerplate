package com.orangepenguin.boilerplate.screens.username;

import android.widget.Toast;

import com.orangepenguin.boilerplate.BasePresenter;
import com.orangepenguin.boilerplate.BaseViewInterface;
import com.orangepenguin.boilerplate.model.User;
import com.orangepenguin.boilerplate.rx.RxSchedulers;
import com.orangepenguin.boilerplate.singletons.Constants;
import com.orangepenguin.boilerplate.usecase.UserUseCase;
import com.orangepenguin.boilerplate.util.NotificationUtil;
import com.orangepenguin.boilerplate.util.SharedPreferencesUtil;

import java.util.concurrent.atomic.AtomicReference;

import javax.inject.Inject;

import rx.Subscription;
import timber.log.Timber;

import static com.orangepenguin.boilerplate.screens.username.UsernamePresenter.PresenterState
        .INITIAL_PAGE_REQUEST_IN_PROGRESS;
import static com.orangepenguin.boilerplate.screens.username.UsernamePresenter.PresenterState.RESPONSE_ERROR;
import static com.orangepenguin.boilerplate.screens.username.UsernamePresenter.PresenterState.RESPONSE_RECEIVED;
import static com.orangepenguin.boilerplate.singletons.Constants.PREF_USERNAME;

public class UsernamePresenter extends BasePresenter {

    private final UserUseCase userUseCase;
    private final SharedPreferencesUtil sharedPreferencesUtil;
    private final NotificationUtil notificationUtil;
    private final AtomicReference<User> user = new AtomicReference<>();
    private final RxSchedulers schedulers;
    PresenterState presenterState = PresenterState.REQUEST_NOT_SENT;
    Subscription subscription;
    private UsernameView view;

    @Inject
    public UsernamePresenter(UserUseCase userUseCase, SharedPreferencesUtil sharedPreferencesUtil, NotificationUtil
            notificationUtil, RxSchedulers schedulers) {
        this.userUseCase = userUseCase;
        this.sharedPreferencesUtil = sharedPreferencesUtil;
        this.notificationUtil = notificationUtil;
        this.schedulers = schedulers;
    }

    @Override
    public void onTakeView(BaseViewInterface takeView) {
        // TODO: this ugly cast is here instead of parametrizing BasePresenter
        view = (UsernameView) takeView;
        String savedUsername = sharedPreferencesUtil.getPreference(PREF_USERNAME, null);
        if (savedUsername != null) {
            view.checkRememberCheckbox();
            view.setUsername(savedUsername);
        }
        setViewState();
    }

    public void showUserButtonPressed(String username, boolean rememberChecked) {
        notificationUtil.showToast(String.format("showUserButtonPressed called with %s, %s", username,
                rememberChecked), Toast.LENGTH_SHORT);

        // example of preference setting
        if (rememberChecked) {
            sharedPreferencesUtil.savePreference(Constants.PREF_USERNAME, username);
        } else {
            sharedPreferencesUtil.clearPreference(Constants.PREF_USERNAME);
        }

        fetchUser(username.trim());
    }

    private void setViewState() {
        // when the response comes back, the View might have stopped and been nulled out in the Presenter (e.g. app
        // backgrounded. In that case, do nothing. Update View state once a View re-attaches.
        // this is the kind of error-prone crazy footwork that would not be required in MVVM
        if (view == null) {
            return;
        }
        switch (presenterState) {
            case INITIAL_PAGE_REQUEST_IN_PROGRESS:
                view.showLoadingIndicator();
                break;
            case RESPONSE_RECEIVED:
                view.hideLoadingIndicator();
                view.startDetailsActivity(user.get());
                break;
            case RESPONSE_ERROR:
                view.hideLoadingIndicator();
                view.setUsernameError("No such user");
        }
    }

    private void fetchUser(String username) {
        view.showLoadingIndicator();

        presenterState = INITIAL_PAGE_REQUEST_IN_PROGRESS;
        subscription = userUseCase
                .fetchUser(username)
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.mainThread())
                .doOnNext(user -> {
                    this.user.set(user);
                    presenterState = RESPONSE_RECEIVED;
                })
                .doOnError(t -> {
                    presenterState = RESPONSE_ERROR;
                    Timber.d(t, "error fetching user");
                    // TODO: figure out type of error and do the right thing:
                    // 1 - no network, 2 - network error, 3 - bad username
                })
                .doOnTerminate(this::setViewState)
                .subscribe();

    }

    /**
     * Represents the state of the presenter.
     */
    enum PresenterState {
        // Initial state - no request has been sent.
        REQUEST_NOT_SENT,
        // The request to fetch the initial page has been sent
        INITIAL_PAGE_REQUEST_IN_PROGRESS,
        // A request to fetch the next page has been sent
        NEXT_PAGE_REQUEST_IN_PROGRESS,
        // The response to either the initial or next page request has been received. All is well.
        RESPONSE_RECEIVED,
        RESPONSE_ERROR
    }
}
