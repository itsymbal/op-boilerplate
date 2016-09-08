package com.orangepenguin.boilerplate;

import android.os.Parcelable;

public interface BasePresenterInterface {
    void showMessage(String format, Object... params);

    void showDebugMessage(String format, Object... params);

    void setView(BaseViewInterface view);

    void onDestroy();
    Parcelable onSaveState();
    void restoreState(Parcelable state);
}
