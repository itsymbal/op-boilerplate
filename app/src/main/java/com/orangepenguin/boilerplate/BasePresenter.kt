package com.orangepenguin.boilerplate

import android.os.Parcelable
import rx.Subscription
import rx.subscriptions.CompositeSubscription

abstract class BasePresenter {

    public var view: BaseViewInterface? = null

    private var compositeSubscription: CompositeSubscription? = null

    /**
     * Initialize the Presenter, using the supplied optional state. This should be called from
     * [android.support.v4.app.Fragment.onCreate] or
     * [android.app.Activity.onCreate] For views this should be called in
     * [android.view.View.onAttachedToWindow]. See [Android
     * leak pattern: subscriptions in views](https://medium
      .com/square-corner-blog/android-leak-pattern-subscriptions-in-views-18f0860aa74c)
     * @param state Optional state, returned previously from `saveInstanceState()`
     */
    fun initialize(state: Parcelable?) {
        onInitialize(state)
    }

    /**
     * Allow Presenter to save its state. In Activity and Fragment this should be called in `onSaveInstanceState`
     */
    fun saveInstanceState(): Parcelable? {
        return onSaveInstanceState()
    }

    /**
     * Start the process of obtaining and displaying information in the View. By the time this
     * method is called the View should be ready to receive update calls from Presenter. This
     * should be called from [android.support.v4.app.Fragment.onStart] or
     * [android.app.Activity.onStart].

     * @param view The view which is bound to the presenter.
     */
    fun takeView(view: BaseViewInterface) {
        this.view = view
        onTakeView(view)
    }

    /**
     * Stops interaction with the view. Once this is called the view will no longer be valid
     * until [BasePresenter.takeView] is called again. This should be called from
     * [android.support.v4.app.Fragment.onStop] or [android.app.Activity.onStop].
     */
    fun dropView() {
        view = null
        onDropView()
    }

    /**
     * Terminate the Presenter. The presenter will never be used again. This should be called
     * from [android.support.v4.app.Fragment.onDestroy] or
     * [android.app.Activity.onDestroy] but only if Activity is being destroyed
     * permanently ([android.app.Activity.isChangingConfigurations] returns false). For
     * views this should be called from [android.view.View.onDetachedFromWindow]
     */
    fun terminate() {
        if (compositeSubscription != null) {
            compositeSubscription!!.clear()
            compositeSubscription = null
        }
        onTerminate()
    }

    /**
     * Subclasses should call this method to add a [Subscription] to a list to be
     * unsubscribed.

     * @param subscription subscription to add to the composite subscription
     */
    fun addDisposableSubscription(subscription: Subscription) {
        if (compositeSubscription == null) {
            compositeSubscription = CompositeSubscription()
        }
        compositeSubscription!!.add(subscription)
    }

    /**
     * Remove supplied subscription from the composite subscription. This method can only be called
     * after `addDisposableSubscription`.
     * @param subscription subscription to remove from the composite subscription
     */
    protected fun removeDisposableSubscription(subscription: Subscription) {
        if (compositeSubscription == null) {
            throw RuntimeException(
                    "removeDisposableSubscription() called when there is no Composite")
        }
        compositeSubscription!!.remove(subscription)
    }

    /**
     * Implementation of presenter initialization. Subclasses should override this method if
     * necessary, e.g. to restore state.
     */
    protected open fun onInitialize(state: Parcelable?) {}

    /**
     * If the Presenter needs to save state, it should override this method. The Parcelable will
     * then be passed to the next Presenter as a parameter to
     * [BasePresenter.onInitialize]. Overriding it is optional therefore it is
     * not abstract.
     */
    protected open fun onSaveInstanceState(): Parcelable? {
        return null
    }

    /**
     * Start processing any logic necessary for this View. This is a good place to subscribe to
     * Observables from Use Cases, request location, etc. The view will be valid until
     * [BasePresenter.onDropView].
     */
    protected open fun onTakeView(view: BaseViewInterface) {}

    /**
     * Stop processing any logic on the view. This is a good place to stop observables which
     * interact with the view. The view will be invalid at this point and will not be valid
     * again until [BasePresenter.onTakeView].
     */
    protected open fun onDropView() {}

    /**
     * Implementation of presenter termination. Subclasses should override this method to stop all
     * work in progress.
     */
    protected open fun onTerminate() {}
}
