package com.orangepenguin.boilerplate.mvp.userdetails;

import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Scope
@Retention(RUNTIME)
@interface UserDetailsScope {
}