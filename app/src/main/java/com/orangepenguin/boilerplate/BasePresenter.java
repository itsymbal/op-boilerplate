package com.orangepenguin.boilerplate;

import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public abstract class BasePresenter<T> {

    @Nullable protected T view;

    @Nullable
    private CompositeSubscription compositeSubscription;

    /**
     * Initialize the Presenter, using the supplied optional state. This should be called from
     * {@link android.support.v4.app.Fragment#onCreate(Bundle)} or
     * {@link android.app.Activity#onCreate(Bundle)} For views this should be called in
     * {@link android.view.View#onAttachedToWindow()}. See <a href="https://medium
     * .com/square-corner-blog/android-leak-pattern-subscriptions-in-views-18f0860aa74c">Android
     * leak pattern: subscriptions in views</a>
     *
     * @param state Optional state, returned previously from {@code saveInstanceState()}
     */
    public final void initialize(@Nullable Parcelable state) {
        onInitialize(state);
    }

    /**
     * Allow Presenter to save its state. In Activity and Fragment this should be called in {@code
     * onSaveInstanceState}
     */
    public Parcelable saveInstanceState() {
        return onSaveInstanceState();
    }

    /**
     * Start the process of obtaining and displaying information in the View. By the time this
     * method is called the View should be ready to receive update calls from Presenter. This
     * should be called from {@link android.support.v4.app.Fragment#onStart()} or
     * {@link android.app.Activity#onStart()}.
     *
     * @param view The view which is bound to the presenter.
     */
    public final void takeView(@NonNull T view) {
        this.view = view;
        onTakeView(view);
    }

    /**
     * Stops interaction with the view. Once this is called the view will no longer be valid
     * until {@link BasePresenter#takeView(Object)} is called again. This should be called from
     * {@link android.support.v4.app.Fragment#onStop()} or {@link android.app.Activity#onStop()}.
     */
    public final void dropView() {
        view = null;
        onDropView();
    }

    /**
     * Terminate the Presenter. The presenter will never be used again. This should be called
     * from {@link android.support.v4.app.Fragment#onDestroy()} or
     * {@link android.app.Activity#onDestroy()} but only if Activity is being destroyed
     * permanently ({@link android.app.Activity#isChangingConfigurations()} returns false). For
     * views this should be called from {@link android.view.View#onDetachedFromWindow()}
     */
    public final void terminate() {
        if (compositeSubscription != null) {
            compositeSubscription.clear();
            compositeSubscription = null;
        }
        onTerminate();
    }

    /**
     * Subclasses should call this method to add a {@link Subscription} to a list to be
     * unsubscribed.
     *
     * @param subscription subscription to add to the composite subscription
     */
    protected void addDisposableSubscription(@NonNull Subscription subscription) {
        if (compositeSubscription == null) {
            compositeSubscription = new CompositeSubscription();
        }
        compositeSubscription.add(subscription);
    }

    /**
     * Remove supplied subscription from the composite subscription. This method can only be called
     * after {@code addDisposableSubscription}.
     *
     * @param subscription subscription to remove from the composite subscription
     */
    protected void removeDisposableSubscription(@NonNull Subscription subscription) {
        if (compositeSubscription == null) {
            throw new RuntimeException(
                    "removeDisposableSubscription() called when there is no Composite");
        }
        compositeSubscription.remove(subscription);
    }

    /**
     * Implementation of presenter initialization. Subclasses should override this method if
     * necessary, e.g. to restore state.
     */
    protected void onInitialize(@Nullable Parcelable state) {}

    /**
     * If the Presenter needs to save state, it should override this method. The Parcelable will
     * then be passed to the next Presenter as a parameter to
     * {@link BasePresenter#onInitialize(Parcelable)}. Overriding it is optional therefore it is
     * not abstract.
     */
    @Nullable
    protected Parcelable onSaveInstanceState() {
        return null;
    }

    /**
     * Start processing any logic necessary for this View. This is a good place to subscribe to
     * Observables from Use Cases, request location, etc. The view will be valid until
     * {@link BasePresenter#onDropView()}.
     */
    protected void onTakeView(@NonNull T view) {}

    /**
     * Stop processing any logic on the view. This is a good place to stop observables which
     * interact with the view. The view will be invalid at this point and will not be valid
     * again until {@link BasePresenter#onTakeView(Object)}.
     */
    protected void onDropView() {}

    /**
     * Implementation of presenter termination. Subclasses should override this method to stop all
     * work in progress.
     */
    protected void onTerminate() {}
}
