package com.orangepenguin.boilerplate.singletons

import com.crashlytics.android.Crashlytics
import com.orangepenguin.boilerplate.Application
import com.orangepenguin.boilerplate.util.ProductionLogTree
import io.fabric.sdk.android.Fabric
import timber.log.Timber

class BoilerplateApplication : Application() {
    override fun setUpLogging() {
        Fabric.with(this, Crashlytics())
        Timber.plant(ProductionLogTree())
    }
}
