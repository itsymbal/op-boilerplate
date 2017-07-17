package com.orangepenguin.boilerplate.rx

import rx.Scheduler
import rx.schedulers.Schedulers

class RxTestSchedulers : RxSchedulers {
    override fun io(): Scheduler = Schedulers.immediate()
    override fun mainThread(): Scheduler = Schedulers.immediate()
}
