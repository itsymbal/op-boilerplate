package com.orangepenguin.boilerplate.mvp;

import android.support.annotation.CallSuper;

import com.orangepenguin.boilerplate.BasePresenterInterface;
import com.orangepenguin.boilerplate.BaseViewInterface;
import com.orangepenguin.boilerplate.singletons.ApplicationInterface;

import javax.inject.Inject;

import rx.Subscription;

import static com.orangepenguin.boilerplate.BuildConfig.DEBUG;

public abstract class BasePresenter implements BasePresenterInterface {
    protected Subscription subscription; // possibly replace with a CompositeSubscription
    @Inject
    ApplicationInterface application;
    private PresenterState presenterState = PresenterState.REQUEST_NOT_IN_PROCESS;
    private BaseViewInterface view;

    @Override
    public void showMessage(String format, Object... params) {
        application.showMessage(format, params);
    }

    @Override
    public void showDebugMessage(String format, Object... params) {
        if (DEBUG) showMessage(format, params);
    }

    @CallSuper
    public void setView(BaseViewInterface view) {
        this.view = view;
    }

    // View will call this method when the view is being destroyed permanently, never to return
    // again. Override this callback method in order to do any necessary cleanup, e.g. close file
    // handles, databases, network streams, unsubscribe from subscriptions
    @Override
    @CallSuper
    public void onDestroy() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

    public enum PresenterState {
        REQUEST_IN_PROCESS,
        REQUEST_NOT_IN_PROCESS
    }
}
