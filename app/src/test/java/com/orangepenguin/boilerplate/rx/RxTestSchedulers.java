package com.orangepenguin.boilerplate.rx;

import rx.Scheduler;
import rx.schedulers.Schedulers;

public final class RxTestSchedulers implements RxSchedulers {
    @Override
    public Scheduler io() {
        return Schedulers.immediate();
    }

    @Override
    public Scheduler mainThread() {
        return Schedulers.immediate();
    }
}
