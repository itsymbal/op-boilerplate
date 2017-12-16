package com.orangepenguin.boilerplate.screens.username

import com.orangepenguin.boilerplate.BaseViewInterface
import com.orangepenguin.boilerplate.model.User

interface UsernameView : BaseViewInterface {
    fun startDetailsActivity(user: User)
    fun setUsername(s: String)
    fun checkRememberCheckbox()
    fun setUsernameError(error: String)
}
