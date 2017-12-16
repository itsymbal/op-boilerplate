package com.orangepenguin.boilerplate.screens.userdetails

import com.orangepenguin.boilerplate.BaseViewInterface

interface UserDetailsView : BaseViewInterface {
    fun setUsername(username: String)
    fun setCompany(company: String)
    fun setName(name: String)
    fun setAvatarUrl(avatarUrl: String)
}
