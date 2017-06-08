package com.orangepenguin.boilerplate.di;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * This corresponds to the scope of the life of a View. The scope spans from first creation of the
 * View, across configuration changes, and until eventual permanent destruction of the View.
 * Specifically, multiple configuration change-caused instantiations of an Activity all belong to
 * the same PerScope.
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface PerScreen {}
