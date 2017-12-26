package com.orangepenguin.boilerplate.singletons

abstract class Constants {
    companion object {
        // field part of a companion object without custom accessors makes them static. @JvmField makes public
        @JvmField val PREF_USERNAME = "PREF_USERNAME"
    }
}
