package com.orangepenguin.boilerplate.rx

import rx.Scheduler

interface RxSchedulers {
    fun io(): Scheduler
    fun mainThread(): Scheduler
}
