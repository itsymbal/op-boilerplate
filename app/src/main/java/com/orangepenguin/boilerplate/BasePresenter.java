package com.orangepenguin.boilerplate;

import android.os.Parcelable;
import android.support.annotation.CallSuper;

import com.orangepenguin.boilerplate.di.Injector;

import javax.inject.Inject;

import rx.Subscription;

import static com.orangepenguin.boilerplate.BuildConfig.DEBUG;

public abstract class BasePresenter implements BasePresenterInterface {
    public Subscription subscription; // possibly replace with a CompositeSubscription - common storage
    @Inject protected ApplicationInterface application;
    private BaseViewInterface view;

    public BasePresenter() {
        Injector.getPresenterComponent().inject(this);
    }

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
        view = null; // null out View reference so it can be destroyed, even while Presenter may still be referenced
        // by some inner Subscriber

    }

    public Parcelable onSaveState() {
        return null;
    }

    public void restoreState(Parcelable state) {}

    public enum PresenterState {
        REQUEST_NOT_SENT,
        REQUEST_IN_PROCESS,
        REQUEST_NOT_IN_PROCESS,
        RESPONSE_RECEIVED
    }
}
