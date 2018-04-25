package com.orangepenguin.boilerplate.util

import org.mockito.Mockito
import org.mockito.stubbing.OngoingStubbing

fun <T> any(): T {
    Mockito.any<T>()
    return uninitialized()
}

fun <T> eq(value: T): T {
    Mockito.eq<T>(value)
    return uninitialized()
}

fun <T> upon(methodCall: T): OngoingStubbing<T> {
    return Mockito.`when`(methodCall)
}

private fun <T> uninitialized(): T = null as T

