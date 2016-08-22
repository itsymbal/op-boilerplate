package com.orangepenguin.boilerplate;

public interface BasePresenterInterface {
    void showMessage(String format, Object... params);

    void showDebugMessage(String format, Object... params);

    void setView(BaseViewInterface view);

    void onDestroy();
}
