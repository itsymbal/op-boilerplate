package com.orangepenguin.boilerplate.singletons

import com.orangepenguin.boilerplate.Application

import timber.log.Timber

class BoilerplateApplication : Application() {
    override fun setUpLogging() {
        Timber.plant(object : Timber.DebugTree() {
            override fun createStackElementTag(element: StackTraceElement): String {
                return super.createStackElementTag(element) + ":" + element.lineNumber
            }
        })
    }
}
