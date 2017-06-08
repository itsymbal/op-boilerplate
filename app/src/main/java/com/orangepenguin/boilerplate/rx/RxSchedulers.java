package com.orangepenguin.boilerplate.rx;

import rx.Scheduler;

public interface RxSchedulers {
    Scheduler io();

    Scheduler mainThread();
}
