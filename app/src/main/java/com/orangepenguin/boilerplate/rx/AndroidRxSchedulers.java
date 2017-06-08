package com.orangepenguin.boilerplate.rx;

import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public final class AndroidRxSchedulers implements RxSchedulers {
    @Override
    public Scheduler io() {
        return Schedulers.io();
    }

    @Override
    public Scheduler mainThread() {
        return AndroidSchedulers.mainThread();
    }
}
