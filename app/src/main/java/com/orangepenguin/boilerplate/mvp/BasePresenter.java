package com.orangepenguin.boilerplate.mvp;


public abstract class BasePresenter {

    // View will call this method when the view is being destroyed permanently, never to return
    // again. Override this callback method in order to do any necessary cleanup, e.g. close file
    // handles, databases, network streams, unsubscribe from subscriptions
    public void onDestroy() {
    }
}
