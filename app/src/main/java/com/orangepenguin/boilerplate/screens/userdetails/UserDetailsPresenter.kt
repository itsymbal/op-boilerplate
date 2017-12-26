package com.orangepenguin.boilerplate.screens.userdetails

import android.os.Parcelable

import com.orangepenguin.boilerplate.BasePresenter
import com.orangepenguin.boilerplate.model.User

import javax.inject.Inject

/**
 * This example presenter requests data for passed in username, keeps state of the request, and sets the state on
 * the view that is set at the time of event
 */
open class UserDetailsPresenter
@Inject constructor() : BasePresenter() {

    // TODO: replace with MVVM implementation
    var user: User? = null
        set(value) {
            val myView = super.view as UserDetailsView?
            field = value
            myView?.setUsername(value?.login() ?: "")
            myView?.setName(value?.name() ?: "")
            myView?.setCompany(value?.company() ?: "")
            myView?.setAvatarUrl(value?.avatarUrl() ?: "")
        }

    public override fun onSaveInstanceState(): Parcelable? {
        return user
    }
}
