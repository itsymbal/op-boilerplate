package com.orangepenguin.boilerplate.screens.userdetails;

import com.orangepenguin.boilerplate.BaseViewInterface;

public interface UserDetailsView extends BaseViewInterface {
    void setUsername(String username);
    void setCompany(String company);
    void setName(String name);
    void setAvatarUrl(String avatarUrl);
}
