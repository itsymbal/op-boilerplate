package com.orangepenguin.boilerplate.mvp.userdetails;

import com.orangepenguin.boilerplate.BasePresenterInterface;
import com.orangepenguin.boilerplate.BaseViewInterface;

interface UserDetailsContract {

    interface View extends BaseViewInterface {
    }

    interface Presenter extends BasePresenterInterface {
        void setView(View view, String username);
    }
}
